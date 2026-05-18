package engine;


import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Ανοίγεις την ΚΛΑΣΗ (Αυτό έλειπε!)
public class WorldBuilder {

    // Εδώ βάζεις τη μέθοδό σου
    public Map<String, Room> buildWorld(String filePath) {
        Map<String, Room> world = new HashMap<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject fullJson = new JSONObject(content);
            JSONArray roomsArray = fullJson.getJSONArray("rooms");

            // 1ο Πέρασμα: Δημιουργία Δωματίων, Items & NPCs
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomData = roomsArray.getJSONObject(i);
                String id = roomData.getString("id");
                String name = roomData.optString("name", id); // Παίρνει name ή id αν λείπει
                String description = roomData.getString("description");

                Room room = new Room(name, description);
                world.put(id, room);

                // --- Διαβάζει Items (υπάρχων κώδικας) ---
                if (roomData.has("items")) {
                    JSONArray items = roomData.getJSONArray("items");
                    for (int j = 0; j < items.length(); j++) {
                        JSONObject itemData = items.getJSONObject(j);
                        String itemName = itemData.getString("name");
                        String itemDesc = itemData.getString("description");
                        boolean isCarryable = itemData.optBoolean("carryable", true);

                        if (isCarryable) {
                            String useMsg = itemData.optString("useText", "You utilize the " + itemName + ", but nothing special happens.");
                            room.addItem(new CarryableItem(itemName, itemDesc, useMsg));
                        } else {
                            room.addItem(new StaticItem(itemName, itemDesc));
                        }
                    }
                }

                // --- ΠΡΟΣΘΗΚΗ: Διαβάζει NPCs ---
                if (roomData.has("npcs")) {
                    JSONArray npcs = roomData.getJSONArray("npcs");
                    for (int j = 0; j < npcs.length(); j++) {
                        JSONObject npcData = npcs.getJSONObject(j);
                        String npcId = npcData.getString("id");
                        String npcName = npcData.getString("name");
                        String npcDesc = npcData.getString("description");

                        // Διαβάζει το αρχικό state από το string
                        NPC.State initState = NPC.State.valueOf(npcData.getString("initialState").toUpperCase());

                        NPC npc = new NPC(npcId, npcName, npcDesc, initState);

                        // Διαβάζει το λεξικό dialogues
                        JSONObject dialogues = npcData.getJSONObject("dialogues");
                        for (String stateStr : dialogues.keySet()) {
                            NPC.State state = NPC.State.valueOf(stateStr.toUpperCase());
                            npc.addDialogue(state, dialogues.getString(stateStr));
                        }
                        room.addNPC(npc);
                    }
                }

                // (Υπάρχων κώδικας για hasWater, lockedObjects, winRoom)
                if (roomData.has("lockedObjects")) { /* ... */ }
                if (roomData.has("hasWater")) { /* ... */ }
                if (roomData.has("isWinRoom")) { /* ... */ }
            }

            // 2ο Πέρασμα: Σύνδεση Εξόδων & Validation
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomData = roomsArray.getJSONObject(i);
                Room currentRoom = world.get(roomData.getString("id"));
                JSONObject exits = roomData.getJSONObject("exits");

                for (String directionString : exits.keySet()) {
                    Direction dir = Direction.valueOf(directionString.toUpperCase());
                    String targetRoomId = exits.getString(directionString);
                    Room targetRoom = world.get(targetRoomId);

                    // VALIDATION: Έλεγχος αν το δωμάτιο προορισμού υπάρχει!
                    if (targetRoom == null) {
                        System.err.println("WARNING: Broken Link! Room '" + currentRoom.getName() + "' tries to go " + dir + " to '" + targetRoomId + "', but that room doesn't exist!");
                        continue; // Προχωράμε στην επόμενη έξοδο χωρίς να κρασάρει το πρόγραμμα
                    }

                    currentRoom.setExit(dir, targetRoom);
                }
            }
            System.out.println("Successful loading " + world.size() + " rooms!");

        } catch (Exception e) {
            System.err.println("Error reading JSON: " + filePath + " (" + e.getMessage() + ")");
        }
        return world;
    }
}