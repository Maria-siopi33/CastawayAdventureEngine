package engine;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WorldBuilder {

    public Map<String, Room> buildWorld(String filePath) {
        Map<String, Room> world = new HashMap<>();

        try {
            // Διαβάζουμε το αρχείο ως κείμενο
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Μετατρέπουμε το κείμενο σε αντικείμενο JSON
            JSONObject fullJson = new JSONObject(content);
            JSONArray roomsArray = fullJson.getJSONArray("rooms");

            // Δημιουργία όλων των δωματίων
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomData = roomsArray.getJSONObject(i);
                String id = roomData.getString("id");
                String description = roomData.getString("description");

                Room room = new Room(id, description);
                world.put(id, room);
            }

            //Σύνδεση των εξόδων (αφού υπάρχουν ήδη όλα τα Rooms)
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomData = roomsArray.getJSONObject(i);
                String id = roomData.getString("id");
                Room currentRoom = world.get(id);

                JSONObject exits = roomData.getJSONObject("exits");
                for (String directionString : exits.keySet()) {
                    String targetRoomId = exits.getString(directionString);


                    Direction dir = Direction.valueOf(directionString.toUpperCase());
                    Room targetRoom = world.get(targetRoomId);

                    currentRoom.setExit(dir, targetRoom);
                }
            }

            System.out.println("Επιτυχής δυναμική φόρτωση " + world.size() + " δωματίων!");

        } catch (Exception e) {
            System.out.println("Σφάλμα κατά τη φόρτωση του κόσμου: " + e.getMessage());
            e.printStackTrace();
        }

        return world;
    }
}