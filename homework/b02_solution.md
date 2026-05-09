# 01 Git-Spiel - Branches

### 1. Merge

Mit `git diff origin/master origin/end --oneline` wurde verglichen, welche Dateien sich in _master_ und _end_ verändert haben. Nach dem Ausschlussverfahren sind so alle Dateien bekannt, die ich nicht verändert haben. Ich habe **shopkeeper.md** mit einer zeile Text bearbeitet und anschließend einen Commit mit dieser Änderung auf dem _master_-Branch durchgeführt.

Im Anschluss wurden beide Branches (_master_ & _end_) mit `git merge origin/end -m "merge message"` gemerged. Der HEAD ist nach wie vor auf dem _master_-Branch.

### 2. 