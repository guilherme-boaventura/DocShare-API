package br.ucsal.docshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.ucsal.docshare.entity.User;
import br.ucsal.docshare.repository.FolderRepository;
import br.ucsal.docshare.repository.UserRepository;
import br.ucsal.docshare.util.exception.RecordNotFoundException;

@RestController
@RequestMapping(path="/api/folder")
public class FolderController {
	
	@Autowired
	private FolderRepository folderRepo;

	@Autowired
	private UserRepository userRepo;
	
	public record FolderDto(String name, String visibility, String tag) {}
	
	@GetMapping(path="/getByUser")
	public String teste(@RequestParam(name="userId") Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
		
		return new Gson().toJson(folderRepo.findByUser(user));
	}

	@PostMapping(path="/createFolder")
	public String insira(@RequestBody FolderDto folder) {
		return userRepo.findByUsername("guilherme.boaventura").get().getUsername();
	}
	
}
