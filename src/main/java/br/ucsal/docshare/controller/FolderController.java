package br.ucsal.docshare.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.ucsal.docshare.entity.File;
import br.ucsal.docshare.entity.Folder;
import br.ucsal.docshare.entity.Tag;
import br.ucsal.docshare.entity.User;
import br.ucsal.docshare.repository.FileContentRepository;
import br.ucsal.docshare.repository.FileRepository;
import br.ucsal.docshare.repository.FolderRepository;
import br.ucsal.docshare.repository.TagRepository;
import br.ucsal.docshare.repository.UserRepository;
import br.ucsal.docshare.util.data.FolderVisibility;
import br.ucsal.docshare.util.exception.RecordNotFoundException;

@RestController
@RequestMapping(path="/api/folder")
public class FolderController {
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private FileContentRepository fileContentRepo;
	
	@Autowired
	private FolderRepository folderRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TagRepository tagRepo;
	
	public record FolderDto(Long id, String name, String visibility, String tag, Long referenceFolderId) {}
	
	@GetMapping(path="/getUserFolders")
	public String getUserFolders(@AuthenticationPrincipal UserDetails userDetails) {
		User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RecordNotFoundException("User not found"));
		
		List<Folder> usrFolders = folderRepo.findByUser(user);
		
		usrFolders.stream().filter(f -> Objects.nonNull(f.getReferenceFolder())).forEachOrdered(f -> {
			
			if(FolderVisibility.PRIVATE.equals(f.getReferenceFolder().getVisibility()) || 
					(FolderVisibility.DEPARTMENTAL.equals(f.getReferenceFolder().getVisibility()) && !f.getUser().getDepartment().equals(f.getReferenceFolder().getUser().getDepartment()))) {
				usrFolders.remove(f);
				folderRepo.delete(f);
			} else {
				f.setName(f.getReferenceFolder().getName());
				f.setTag(f.getReferenceFolder().getTag());
			}
			
		});
		
		return new Gson().toJson(usrFolders);
	}
	
	@GetMapping(path="/getSharedFolders")
	public String getSharedFolders(@AuthenticationPrincipal UserDetails userDetails) {
		
		List<Folder> sharedFolders = folderRepo.findSharedFolders().stream()
																   .filter(f -> ((FolderVisibility.DEPARTMENTAL.equals(f.getVisibility()) && f.getUser().getDepartment().equals(userRepo.findByUsername(userDetails.getUsername()).get().getDepartment())) 
																		   		|| FolderVisibility.CORPORATIVE.equals(f.getVisibility()))
																		   		&& Objects.isNull(f.getReferenceFolder()))
																   .collect(Collectors.toList());
		
		sharedFolders.forEach(f -> {
			f.setVisibility(FolderVisibility.get(f.getVisibility().getAcronym()));
		});
		
		return new Gson().toJson(sharedFolders);
	}

	@PostMapping(path="/saveFolder")
	public String saveFolder(@RequestBody FolderDto folderDto, @AuthenticationPrincipal UserDetails userDetails) {
		if(Objects.isNull(folderDto.id)) {

			Folder referenceFolder = (Objects.nonNull(folderDto.referenceFolderId))? folderRepo.findById(folderDto.referenceFolderId).orElseGet(null) : null;;
			Tag tag = (Objects.nonNull(folderDto.tag))? tagRepo.findByAcronym(folderDto.tag).orElseGet(null) : null;
			
			Folder folder = new Folder(folderDto.name, userRepo.findByUsername(userDetails.getUsername()).get(), FolderVisibility.get(folderDto.visibility), tag, referenceFolder);

			return new Gson().toJson(folderRepo.save(folder));

		} else {
			Folder folder = folderRepo.findById(folderDto.id).get();
			
			folder.setName(folderDto.name);
			folder.setVisibility(FolderVisibility.get(folderDto.visibility));
			folder.setTag(tagRepo.findByAcronym(folderDto.tag).get());
			
			return new Gson().toJson(folderRepo.save(folder));
		}
	}
	
	@GetMapping(path="/getVisibilities")
	public String getVisibilities() {
		return new Gson().toJson(FolderVisibility.findAll());
	}
	
	@GetMapping(path="/getTags")
	public String getTags() {
		return new Gson().toJson(tagRepo.findAll());
	}
	
	
	@PostMapping(path="/deleteFolder")
	public String deleteFolder(@RequestParam Long id) {
		Folder folder = folderRepo.findById(id).get();
		
		List<Folder> referenceFolders = folderRepo.findByReferenceFolder(folder);
		
		referenceFolders.forEach(f -> {
			deleteFolderAndFiles(f);
		});
		
		deleteFolderAndFiles(folder);
		
		return new Gson().toJson(folder);
	}
	
	@PostMapping(path="/importFolder")
	public String importFolder(@RequestParam Long id, @AuthenticationPrincipal UserDetails userDetails) {
		Folder refFolder = folderRepo.findById(id).get();
		
		Folder newFolder = new Folder(refFolder.getName(), userRepo.findByUsername(userDetails.getUsername()).get(), refFolder.getVisibility(), refFolder.getTag(), refFolder);
		folderRepo.save(newFolder);
		
		return new Gson().toJson(newFolder);
	}
	
	private void deleteFolderAndFiles(Folder folder) {
		List<File> files = fileRepo.findByFolder(folder);

		files.forEach(f -> {
			fileContentRepo.delete(fileContentRepo.findByFile(f));
			fileRepo.delete(f);
		});
		
		folderRepo.delete(folder);
	}
}
