package br.ucsal.docshare.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import br.ucsal.docshare.entity.File;
import br.ucsal.docshare.entity.FileContent;
import br.ucsal.docshare.repository.FileContentRepository;
import br.ucsal.docshare.repository.FileRepository;
import br.ucsal.docshare.repository.FolderRepository;

@RestController
@RequestMapping(path="/api/file")
public class FileController {
	
	@Autowired
	private FolderRepository folderRepo;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private FileContentRepository fileContentRepo;
	
	
	public record FileUpload(MultipartFile file, String fileName, Long folderId) {}
	
	@PostMapping(path="/saveFile", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Resource> saveFile(@ModelAttribute FileUpload fileDto) throws IOException {
		String originalName = fileDto.file.getOriginalFilename();
		
		File file = new File(originalName, originalName.substring(originalName.lastIndexOf('.')), folderRepo.findById(fileDto.folderId).get());
		fileRepo.save(file);
		
		FileContent content = new FileContent(fileDto.file.getBytes(), file);
		fileContentRepo.save(content);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path="/getByFolder")
	public String getByFolder(@RequestParam Long folderId) {
		return new Gson().toJson(fileRepo.findByFolder(folderRepo.findById(folderId).get()));
	}
	
}
