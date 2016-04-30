
package com.maxtop.walker.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.vo.Player;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@RequestMapping("/list")
	public Collection<Player> getList() {
		return playerRepository.list();
	}
	
}
