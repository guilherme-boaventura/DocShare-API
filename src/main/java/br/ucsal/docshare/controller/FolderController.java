package br.ucsal.docshare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/folder")
public class FolderController {
	
	@GetMapping(path="/test")
	public String teste() {
		return "ok";
	}

}
