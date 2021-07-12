package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.items.Item;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

import java.util.UUID;

public class ItemPickupExecutor extends BasicResponseExecutor {


    public ItemPickupExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {

        System.out.println("ITEM PICKUP!");
        String[] data = resp.data.split("#");
        System.out.println("LENGTH: "+data.length);

        if(data.length == 5) {
            try {
                UUID playerUUID = UUID.fromString(data[0]);
                UUID itemUUID = UUID.fromString(data[3]);
                String itemName = data[1];
                int count = Integer.parseInt(data[2]);
                int id = Integer.parseInt(data[4]);

                if(!MultiplayerGameState.getEntityHashMap().containsKey(itemUUID.toString())) {

                    Item i = new Item(Item.items[id], itemName, id, itemUUID);
                    if(MultiplayerGameState.getUUID().toString().equalsIgnoreCase(playerUUID.toString())) {
                        i.setCount(count);
                    }

                    MultiplayerGameState.getWorld().getEntityManager().getPlayer().getInventory().addItem(i);
                    for(Item item : MultiplayerGameState.getWorld().getItemManager().getItems()) {
                        if(i.getUuid().toString().equalsIgnoreCase(item.getUuid().toString())) {
                            item.setPickedUp(true);
                        }
                    }
                } else {
                }
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
