# 01 Git Status erklären

+ `On branch b03` bedeutet, dass man sich im aktuellen git Repo auf dem branch b03 befindet
+ Der zweite Teil sagt aus, dass **CONTRIBUTING.md** und **homework/b03.md** seit dem letzten commit geändert wurden und noch nicht in der Stagingarea sind
+ Der nächste Teil sagt aus, dass eine Datei namens **foo.java** für git noch unbekannt ist
+ Der letzte Teil sagt, dass noch keine änderungen gestaged wurden, die committet werden könnten

## 01.1 Commit von foo.java

+ `git add foo.java` --> foo.java wird in den Staging-Index eingetragen
+ `git commit -m "V0.X - foo.java added` --> Neuer Commit auf **b03** mit foo.java

# 02 Git-Spiel

Das Repo **prog2_ybel_gitquest** wurde mit `git clone <link>` in IntelliJ kopiert.

## 02.1 Git Info-Tools: checkout, log, show und diff

+ An `tag 01` wurde folgender Text zu **questlog.md** hinzugefügt: *"Mit seinem Schwert in der Hand und und einer leichten Rüstung bekleidet stieg Markus tapfer in den Dungeon ein. Schon bald stieß er auf die ersten Monster - grässliche, rattenähnliche Wesen."* `git show <commit-id von tag 01>`
+ Der Held hatte an `tag 01.3` zum ersten Mal genau 4 Experience-Punkte `git log -G "experience" -p`
+ Markus hatte an `tag 02` zum ersten Mal genau 10 Hunger-Punkte `git log -G "hunger" -p`
+ Insgesamt hatte Markus **2** Heiltränke in seinem Rucksack. Einen hat er einem NPC der von Ratten angegriffen wurde gegeben und den anderen hat er aus einem Gerippe `git log -G "Heiltrank" -p` 
+ Im Shop hat er bereits **1x Zwergenbrot** und **1x Heiltrank** für insgesamt **10** Gold gekauft `git log -G "gold" -i -p`
+ Zwischen `tag 03` und `tag 04` hat Markus **5 Leben** regeneriert, dafür **10 Hunger verbraucht** und **10 Gold** ausgegeben `git diff <commit-id-tag-03> <commit-id-tag-04>`
+ An `tag 3.17` hat Markus ein Brot gegessen und dafür 10 Hungerpunkte abgezogen bekommen `git log -p -- rucksack.md stats.md`

## 02.2 Commit korrigieren

An `tag 04.5` fehlen Exp-Punkte. Wir korrigieren den fehler, indem wir mit `git checkout <commit-id von tag 04.5>` den commit laden, die Punkte in der entsprechenden Datei **stats.md** anpassen und mit `git add stats.md` und `git commit --amend -m "tag 04.5"` neu committen. Jetzt ist an `tag 04.5` die richtige Menge Exp-Points hinterlegt.

## 02.3 Geschichte fortführen

Die Geschichte in **questlog.md** wurde wie folgt erweitert, die Datei mit `git add questlog.md` gestaged und mit `git commit -m "tag 04.6"` committet: *"Er war am Höhepunkt seiner Karriere, als er auf seinen Erzfeind traf; Sven der Normalo. Dieser war im ganzen Reich gefürchtet wie kein Zweiter."*

## 02.4 Geschichte fortführen II

Die Geschichte wurde erneut um einen Tag erweitert. An diesem Tag lief Markus Siegreich durch ein Dorf und bekam geschenke, bis Sven der Normalo auftauchte. Es wurden alle Dateien gestaged (`git add questlog.md stats.md rucksack.md`) und anschließen ein neuer Commit eingecheckt (`git commit -m "tag 04.7"`).

## 02.5 Gear und Stats trennen

Zur Trennung wurden die Werte, die zu Gear gehören sollen in **gear.md** verschoben. Dann wurde mit `git add stats.md gear.md` gestaged und mit `git commit -m "tag 04.8"` committet. Da wir seit 02.2 einen detached HEAD hatten, müssen wir die Commits seit dem auf den master branch ziehen. (`git branch -f master HEAD && git checkout master`)

## 03 Commit-Meldungen verfassen

Bei "Commit 46530b6" weiß ich nicht worum es geht. Es scheint der fünfte commit zum "Layer System" zu sein - was auch immer das ist - allerdings sagt die Commit-Message wenig über den Inhalt aus, listet aber Prozessschritte auf, die nichts Inhaltliches beinhalten.
Bei "Commit 3e37472" 'better' ist subjektiv; 'some' ist ungenau/wenig hilfreich; auch hier muss genauer eingegrenzt werden, was genau gemacht wurde.

## 04 Setup

Das Programm zum Testen der Java-Version ist einfach eine main-Methode um den gegebenen Codeblock "IO.println(System.getProperty("java.version"));" 
Um die Funktionsweise von Spotless zu testen, schreiben wir ein Dummy-Programm ohne Zweck, aber mit vielen Syntaxfehlern. Dann wird die Datei in einem Commit gesichert und `./gradlew spotlessApply` wird ausgeführt. Im Anschluss kann man sich mit `git diff` die Unterschiede der Datei anschauen. Zur Wiederholung der Demo lässt sich die 'schlechte' Datei mit `git checkout -- src/main/java/checks/spotlessDemo.java` wiederherstellen. 