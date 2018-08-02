package com.hit.controller;

import java.util.Observable;

import com.hit.model.Model;
import com.hit.view.View;

public class CacheUnitController implements Controller {

	private Model model;
	private View view;

	public CacheUnitController(View view, Model model) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		 if (o instanceof Model) {
			view.updateUIData(arg);
		} else if (o instanceof View) {
			model.updateModelData(arg);
		}

	}

}
