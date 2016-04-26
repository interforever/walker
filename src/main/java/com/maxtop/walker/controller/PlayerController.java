
package com.maxtop.walker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxtop.walker.vo.Player;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {
	
	@RequestMapping("/list")
	public List<Player> getList() {
		List<Player> players = new ArrayList<Player>();
		Player p1 = new Player();
		p1.setName("abc");
		players.add(p1);
		return players;
	}
	
}
