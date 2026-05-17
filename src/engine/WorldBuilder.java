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

            // 1ο Πέρασμα: Δημιουργία Δωματίων ΚΑΙ Αντικειμένων
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomData = roomsArray.getJSONObject(i);
                String id = roomData.getString("id");
                String description = roomData.getString("description");

                Room room = new Room(id, description);
                // Διαβάζει αν το δωμάτιο έχει νερό (προεπιλογή false αν δεν υπάρχει το πεδίο)
                boolean hasWater = roomData.optBoolean("hasWater", false);
                room.setHasWater(hasWater);

                if (roomData.has("lockedObjects")) {
                    JSONObject locked = roomData.getJSONObject("lockedObjects");
                    for (String objName : locked.keySet()) {
                        room.addLockedObject(objName, locked.getString(objName));
                    }
                }
                // Διαβάζει αν αυτό το δωμάτιο τερματίζει το παιχνίδι
                boolean isWinRoom = roomData.optBoolean("isWinRoom", false);
                room.setWinRoom(isWinRoom);

                if (roomData.has("items")) {
                    JSONArray itemsArray = roomData.getJSONArray("items");
                    for (int j = 0; j < itemsArray.length(); j++) {
                        JSONObject itemData = itemsArray.getJSONObject(j);
                        String name = itemData.getString("name");
                        String itemDesc = itemData.getString("description");
                        boolean isCarryable = itemData.optBoolean("carryable", true);

                        if (isCarryable) {
                            // Διαβάζουμε το μήνυμα χρήσης (ή βάζουμε ένα τυπικό αν δεν υπάρχει στο JSON)
                            String useMsg = itemData.optString("useText", "You try to use the " + name + ", but nothing special happens.");
                            room.addItem(new CarryableItem(name, itemDesc, useMsg));
                        } else {
                            room.addItem(new StaticItem(name, itemDesc));
                        }
                    }
                }
                world.put(id, room);
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
} // 5. Κλείνεις την κλάση