# 01 Git-Spiel - Branches

### 1. Merge

Mit `git diff origin/master origin/end --oneline` wurde verglichen, welche Dateien sich in _master_ und _end_ verändert haben. Nach dem Ausschlussverfahren sind so alle Dateien bekannt, die ich nicht verändert haben. Ich habe **shopkeeper.md** mit einer zeile Text bearbeitet und anschließend einen Commit mit dieser Änderung auf dem _master_-Branch durchgeführt.

Im Anschluss wurden beide Branches (_master_ & _end_) mit `git merge origin/end -m "merge message"` gemerged. Der HEAD ist nach wie vor auf dem _master_-Branch.

### 2. Merge Conflict

Auch hier wurde eine Änderung an einer Datei durchgeführt. Ich habe in **stats.md** ein Attribut hinzugefügt. Beim mergen kam ein Merge Conflict, der zuerst aufgelöst werden musste. Danach lief `git merge` ohne Probleme durch.

### 3. Merge Conflict II

Geändert wurde Slot 1 in **rucksack.md** im _master_-Branch. Hier existiert bereits eine Änderung im _end_-Branch. 

Bei `git merge` und unterschiedlichen Änderungen an der gleichen Stelle bekommt man einen Merge-Conflict, den man erst lösen muss. Bei mehrfach der gleichen Änderung an der gleichen Stelle wird ignoriert. Git schaut also nur nach Zeichen, die sich unterscheiden. Wenn es keinen Unterschied in den einzelnen Zeichen gibt, ist egal, was dort vorher mal stand.

### 4. Rebase

Der Rebase wurde vom _end_-Branch mit `git checkout end` und `git rebase master` durchgeführt. 


