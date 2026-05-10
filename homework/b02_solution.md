# 01 Git-Spiel - Branches

### 1. Merge

Mit `git diff origin/master origin/end --oneline` wurde verglichen, welche Dateien sich in _master_ und _end_ verändert haben. Nach dem Ausschlussverfahren sind so alle Dateien bekannt, die ich nicht verändert haben. Ich habe **shopkeeper.md** mit einer zeile Text bearbeitet und anschließend einen Commit mit dieser Änderung auf dem _master_-Branch durchgeführt. 
Im Anschluss wurden beide Branches (_master_ & _end_) mit `git merge origin/end -m "merge message"` gemerged. Der HEAD ist nach wie vor auf dem _master_-Branch. Es gab keine Meldung o.Ä., da *shopkeeper.md* im _end_-Branch nicht verändert wurde, und so keine Konflikte entstanden sind.

### 2. Merge II

Auch hier wurde eine Änderung an einer Datei durchgeführt. Ich habe in **stats.md** ein Attribut hinzugefügt. Der Command `git merge` lief ohne Probleme durch, da die beiden Dateien zwar bearbeitet wurden, sich aber an unterschiedlichen Stellen unterscheiden und sich nicht in die Quere kommen. 

### 3. Merge Conflict

Geändert wurde Slot 1 in **rucksack.md** im _master_-Branch. Hier existiert bereits eine Änderung im _end_-Branch. 

Bei `git merge` und unterschiedlichen Änderungen an der gleichen Stelle bekommt man einen Merge-Conflict, den man erst lösen muss. Bei mehrfach der gleichen Änderung an der gleichen Stelle wird einfach gemergt, da es sich um exakt die gleiche Änderung handelt und kein Konflikt besteht. Git schaut also nur nach Zeichen, die sich unterscheiden.

### 4. Rebase

Der Rebase wurde vom _end_-Branch mit `git checkout end` und `git rebase master` durchgeführt. Jetzt sitzt _end_ nicht mehr an _tag 04.5_, sondern an der Spitze, also auf dem Head. Dadurch ist _end_ jetzt einfach ein Teil der linearen Commit-Geschichte und kein eigener Branch mehr.

# 02 - Cat-Café

Das Projekt wurde von der Projektseite auf den eigenen Account geforked und mit `git clone` lokal kopiert. Anschließend wurde eine einfache _build.gradle_ und _settings.gradle_ erstellt. In _build.gradle_ sind die Plugins 'java', 'application' und 'com.diffplug.spotless' in der entsprechenden Version eingebunden. Mit `gradle wrapper` wurde _gradlew_ zum einfachen bauen und testen des Projekts erstellt. 

# 03 - JUnit-Tests, Remotes und CI-Workflow

Das Repo [Katzen-Cafe](https://github.com/xSTYX19x/Katzen-Cafe#) wurde durch einen Fork des Originals angelegt. Es wurden folgende Tests formuliert: 
+ Hat ein neu erstelltes Cafe keine Katzen?
+ Steigt die Anzahl der Katzen, wenn eine Katze hinzugefügt wird?
+ Steigt die Anzahl der Katzen, wenn mehrere verschiedene Katzen hinzugefügt werden entsprechend?
+ Wird bei `.getCatByName()` die richtige Katze zurückgegeben?
+ Wird bei `.getCatByName()` ein leeres Katzenobjekt zurückgegeben, wenn keine entsprechende Katze gefunden wurde?
+ Wird bei `.getCatByWeight()` die richtige Katze zurückgegeben?
+ Wird bei `.getCatByWeight()` ein leeres Katzenobjekt zurückgegeben, wenn keine entsprechende Katze gefunden wurde?
+ Wird bei `.getCatByWeight()` ein leeres Katzenobjekt zurückgegeben, wenn _MIN_ größer ist als _MAX_?
+ Wird bei `.getCatByWeight()` ein leeres Katzenobjekt zurückgegeben, wenn _MIN_ größer ist als _MAX_ und das gewicht der Katze außerhalb des Bereichs liegt?
+ Wird dieselbe Katze zweimal angelegt, erscheint sie trotzdem nur einmal im Cafe?

Alle Tests laufen mit _PASSED_ durch.

Als CI-Workflow wurde ein automatisches Kompilieren des Projekts mit `./gradlew build`, ein Durchlaufen der o.g. Tests mit `./gradlew test --tests "catcafe.CatCafeTest"` und ein Check der korrekten Formatierung mit `gradle spotlessCheck` bei einem neuen Commit auf dem **master**-Branch eingeführt.
