
package com.maxtop.walker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxtop.walker.cache.PlayerItemRepository;
import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.model.Player;

@RestController
@RequestMapping(value = "/setting")
public class SettingController {
	
	@RequestMapping(value = "/playerid/{tel}", method = RequestMethod.GET)
	public String getPlayeridByTel(@PathVariable String tel) {
		return null;
	}
	
}
