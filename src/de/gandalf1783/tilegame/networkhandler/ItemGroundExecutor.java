package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.items.Item;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

import java.util.UUID;

public class ItemGroundExecutor extends BasicResponseExecutor {


    public ItemGroundExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        String[] data = resp.data.split("#");
        if(data.length == 6) {
            String itemName = data[3];
            int itemID = Integer.parseInt(data[5]);
            int itemCount = Integer.parseInt(data[4]);
            UUID uuid = UUID.fromString(data[0]);

            Item i = new Item(Item.items[itemID], itemName, itemCount, uuid);
            i.setPosition((int) Float.parseFloat(data[1]), (int) Float.parseFloat(data[2]));

            MultiplayerGameState.getWorld().getItemManager().addItem(i);
            System.out.println("Added item to ground!");
        }
    }
}
