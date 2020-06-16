package io.bssw.psip.ui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.data.Score;
import io.bssw.psip.ui.util.FontSize;
import io.bssw.psip.ui.util.TextColor;
import io.bssw.psip.ui.util.UIUtils;

@Tag("score-group")
public class ScoreItem extends AbstractCompositeField<VerticalLayout, ScoreItem, Item> {
	private List<HorizontalLayout> scoreLayouts = new ArrayList<>();	
	private Item item;

	public ScoreItem(Item item) {
		super(null);
		this.item = item;

		List<Score> scores = item.getCategory().getActivity().getScores();
		
		for (int i = 0; i < scores.size(); i++) {
			Score score = scores.get(i);
			final int idx = i;
			Button button = UIUtils.createPrimaryButton(score.getName());
			button.setWidth("130px");
			button.getElement().getStyle().set("background-color", score.getColor());
			button.addClickListener(e -> setScore(Optional.of(idx)));
			Label label = UIUtils.createLabel(FontSize.M, TextColor.BODY, item.getQuestions().get(i));
			HorizontalLayout scoreLayout = new HorizontalLayout(button, label);
			scoreLayout.setAlignItems(Alignment.CENTER);
			scoreLayout.setWidthFull();
			scoreLayout.setPadding(true);
			getContent().add(scoreLayout);
			scoreLayouts.add(scoreLayout);
		}
		
		resetScores();
		setScore(item.getScore());
	}
	
	/**
	 * Set the border to 1px transparent. Note this may not work on all browsers
	 */
	private void resetScores() {
		for (HorizontalLayout score : scoreLayouts) {
			score.getElement().getStyle().set("border", "1px solid transparent");
		}
	}
	
	/**
	 * Set the score to the supplied value
	 * 
	 * @param idx
	 */
	private void setScore(Optional<Integer> score) {
		resetScores();
		if (score.isPresent()) { 
			scoreLayouts.get(score.get()).getElement().getStyle().set("border", "1px solid black");
			item.setScore(score.get());
		}
	}


	@Override
	protected void setPresentationValue(Item item) {
		setScore(item.getScore());
	}
}