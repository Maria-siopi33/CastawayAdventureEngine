# CastawayAdventureEngine
Κάντε Clone το repository.

Μόλις το ανοίξετε στο IntelliJ, πηγαίνετε: File -> Project Structure -> Libraries.

Πατήστε το +, επιλέξτε Java και βρείτε το αρχείο json-20240303.jar που βρίσκεται μέσα στον φάκελο lib του project.

Προσοχή: Χωρίς αυτό το βήμα, ο WorldBuilder θα βγάζει κόκκινα σφάλματα!

Τι περιλαμβάνει αυτό το update:

Δυναμική φόρτωση δωματίων από το world.json.

Έτοιμο GameContext που κρατάει το πού βρίσκεται ο παίκτης.

Βιβλιοθήκη JSON για το parsing των δεδομένων.


📖 Οδηγίες Χρήσης για την Ομάδα (Commands & Logic)
Για να λειτουργήσουν οι εντολές σας (π.χ. GoCommand, LookCommand), χρησιμοποιήστε τις παρακάτω μεθόδους:

Πού βρίσκεται ο παίκτης:

Χρησιμοποιήστε την context.getCurrentRoom() για να πάρετε το τρέχον δωμάτιο.

Πώς βρίσκουμε τις εξόδους:

Κάθε δωμάτιο έχει τη μέθοδο getExit(String direction).

Παράδειγμα: String nextRoomId = currentRoom.getExit("north");

Αν η μέθοδος επιστρέψει null, σημαίνει ότι δεν υπάρχει έξοδος προς τα εκεί. Αν επιστρέψει ένα ID (π.χ. "Jungle"), τότε ο παίκτης μπορεί να μετακινηθεί.

Πώς αλλάζουμε δωμάτιο:
Μόλις βρείτε το ID του επόμενου δωματίου (π.χ. από το getExit), καλέστε την:
context.setCurrentRoom(world.get(nextRoomId));
Αφού βρείτε το ID του επόμενου δωματίου από το JSON, καλέστε:

context.setCurrentRoom(world.get(nextRoomId));
