package GameState.GUIs;

import CaptainWolfie.UIBlocks.Blocks.UISquare;
import CaptainWolfie.UIBlocks.Colors.UIColor;
import CaptainWolfie.UIBlocks.Utils.Align.UILocation;
import CaptainWolfie.UIBlocks.Utils.Size.UIDimension;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<UISquare> squares = new ArrayList<UISquare>();

    public void init(){
        UISquare first = UISquare.createSquare(UIColor.DARK_GRAY, 7);
        UIColor color = UIColor.DARK_GRAY;
        color.setAlpha(255);
        first.setColor(color);
        first.setSize(new UIDimension(50, 50));
        UILocation location = new UILocation(20, 100f);
        location.setExtraY(-70);
        first.setLocation(location);
        first.setVisible(true);
        squares.add(first);
    }

    public void update(){
        UISquare.updateSquares();
    }

    public void render(Graphics g){
        UISquare.renderSquares(g);
    }

}
