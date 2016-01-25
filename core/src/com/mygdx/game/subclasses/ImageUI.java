package com.mygdx.game.subclasses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageUI extends Image{
	private Sprite ball;
	private boolean draw;
	public int position;
	
	public ImageUI(TextureRegion region, TextureRegion ball, boolean draw, int position){
		super(region);
		addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                System.out.println("Touched: " + event.getListenerActor().getX());
                //setVisible(false);
                return true;
            }
        });
		this.draw = draw;
		this.ball = new Sprite(ball);
		this.position = position;
		this.ball.setSize(getWidth(), getHeight());
		this.ball.setAlpha(0.8f);
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		//System.out.println("hitted");
		return super.hit(x, y, touchable);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (draw) {
			ball.setPosition(getX() + getWidth()/2 - ball.getWidth()/2, getY() + getHeight()/2 - ball.getHeight()/2);
			ball.draw(batch);
		}
	}
	
}