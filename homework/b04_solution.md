# Vorwort

Ich habe die pull requests offen gelassen und bereits den nächsten Teil der aufgabe bearbeitet und sort dann ebenfalls eine Pull request erstellt und das ganze noch ein drittes mal. Das hat irgendwann dazu geführt, dass die gesamte versionsverwaltung einfach unübersichtlich geworden ist. Daher habe ich mich entschieden, das Projekt nochmal neu zu forken und nur den Git-Teil nochmal zu machen. Der Code wurde einfach stück für Stück aus dem alten ins neue Projekt kopiert. 

In Aufgabe 3.4 ist gefordert einen Vergleich zu machen, zwischen dem RegexHighlighter und dem ScanningHighlighter. Da ich den Scanning Highlighter nicht implementieren musste (Aufgabe 2 **ODER** Aufgabe 3) habe ich mich gegen die Bearbeitung dieser Aufgabe entschieden. Ich habe dennoch einen groben theoretischen Vergleich der beiden Highlighting-Methoden aufgeschrieben, da die Konzepte ja auch ohne selbst gecoded zu haben nachvollziehbar verfügbar sind.

# 01 Regex für Syntax-Highlighting

### 01.1 MiniJavaTokens
Das Projekt aus der Aufgabe wurde geforkt und anschließend lokal geklont. Bevor irgendwas am code geändert wurde, habe ich einen neuen Branch _feature/mini-java-tokens_ erstellt, auf dem Aufgabe 1 liegen soll.
Dann habe ich mit der bearbeitung der aufgabe begonnen und mich mit **regular Expressions** auseinandergesetzt. Nachdem ich das Konzept einigermaßen durchdrungen hatte, waren die einzelnen regex's keine große Hürde mehr.

### 01.2 JUnit-Tests

Die nächste Herausforderung lag in den Tests und wie man die neuen Regex's in der Testklasse tatsächlich anspricht. Das habe ich bewerkstelligt, indem ich die übergebene Liste an Tokens in einzelnen Funktionen im Test Stückweise zurückgegeben habe, je nachdem, was gefordert ist. 

Im Anschluss wurde entsprechend Aufgabe 04 eine Pull-Request erstellt, die nun zum Review offen ist.

# 04 CI und Pull Requests

### 04.1 CI-Pipeline

Die CI-Pipeline ist fast vollständig aus der Lösung zu **b02** kopiert, und wurde nur auf die Aufgabenstellung angepasst.

### 04.2 Branches und Pull Requests

Der erste Branch wurde bereits in #01 angelegt; die weiteren folgen dann bei weiterer bearbeitung der Aufgabe.
Auch die erste Pull Request wurde in Aufgabe 1 bereits gestellt; der Rest folgt im Verlauf der Aufgabe.

### 04.3 Gegenseitige Reviews der PR

Momentan warte ich noch auf Reviews meiner Pull-Request; wenn Kommentare eingetroffen und beantwortet, bzw. Ideen/Vorschläge umgesetzt sind, wird gemerged und die Bearbeitung von Aufgabe 03 kann beginnen.
Das gleiche Verfahren wird für die weiteren Aufgabenteile fortgeführt.

# 03 Syntaxhighlighting mit ScanningHighlighter

### 03.1 ScanningHighlighter.collectMatches implementieren

hier habe ich mir zuerst eine Logik überlegt und diese dann in Java umformatiert. Die logik läuft wie folgt:

Eine Schleife durchläuft den Text bis zum Ende. Währenddessen wird nach Matches gesucht. Wrid eins gefunden, wird sich der Beginn und das Ende gemerkt. Sind 2 Token die gefunden wurden gleich Lang, gewinnt das 'wort', was als erstes gefunden wurde. A ende bekommt man eine Liste mit Treffern zurück.

### 03.2 ScanningHighlighter.normalize überschreiben

die funktion gibt die liste unverändert zurück, da die erhaltene liste bereits sortiert und gültig ist.

### 03.3 JUnit-Tests

Die JUnit-Tests wurden entsprechend der anforderungen geschrieben; hier wurde sich stark an den vorherigen Tests orientiert

### 03.4 Vergleich ScanningHighlighter und RegexHighlighter

Der **RegexHighlighter** wendet alle Token unabhängig auf den gesamten Text an und sammelt danach alle Treffer in einer Liste. Danach werden die Regionen sortiert und überlappende Treffer nach einer festen Regel entfernt: Wenn zwei Treffer sich überlappen, bleibt der früher gefundene bzw. früher in der Liste stehende Treffer erhalten.

Der **ScanningHighlighter** läuft den Text dagegen von links nach rechts durch. An jeder Position sucht er nur nach Token, die genau dort beginnen, und nimmt dann das längste passende Token; bei Gleichstand gewinnt das frühere Token in MiniJavaTokens.
Antworten auf die Fragen

Gibt es **Unterschiede** zwischen RegexVariante und ScannerVariante?
Ja. Der Scanner ist positionsgenau und nimmt direkt das längste Match an der aktuellen Stelle. Der RegexHighlighter findet erst alles und räumt Konflikte später auf, dadurch können sich je nach Token-Reihenfolge und Überlappungen andere Entscheidungen ergeben.

Warum treten diese **Unterschiede** auf?
Weil die Strategien unterschiedlich arbeiten: Der Scanner entscheidet lokal beim Durchlaufen des Textes, der RegexHighlighter entscheidet global nach dem Sammeln aller Treffer. Besonders bei überlappenden Treffern, z.B. Javadoc gegen Block-Kommentar, kann das zu anderen Ergebnissen führen.

**Beispiel** für den Unterschied:
Bei `/** ... */` kann der Scanner direkt das längere Javadoc-Token nehmen, während der RegexHighlighter erst beide Treffer findet und dann nach der Konfliktregel auflöst. Wenn die Token-Reihenfolge ungünstig wäre, könnte beim Regex-Ansatz theoretisch der allgemeinere Kommentar-Typ gewinnen.

# 05 Pull-Request Title und Beschreibung

### PR1 - Java Mini Tokens

**TITEL:** `feat: MiniJavaTokens – Regex-Token für Java-Syntax-Highlighting definiert`

**BESCHREIBUNG:** 
## Was wurde geändert?
Implementierung der Token-Liste in `MiniJavaTokens.defaultTokens()` für das 
Java-Syntax-Highlighting. Zusätzlich JUnit-Tests für alle definierten Token.

## Definierte Token
- **String-Literale** – `"..."` inkl. Escape-Sequenzen wie `\"`
- **Char-Literale** – `'.'` inkl. Escape-Sequenzen wie `'\n'`, `'\\'`
- **Einzeilige Kommentare** – `// ...` bis Zeilenende
- **Javadoc-Kommentare** – `/** ... */` (vor Block-Kommentar, damit kein falsches Matching)
- **Block-Kommentare** – `/* ... */` (mehrzeilig via `[\s\S]*?`)
- **Keywords** – alle Java-Keywords als Wortgrenzen-Match (`\b...\b`)
- **Annotationen** – `@Name` mit Buchstaben und optionalem Bindestrich

## Tests
JUnit-Tests in `MiniJavaTokensTest` mit folgenden Fällen pro Token:
- Treffer am Anfang, in der Mitte und am Ende
- Mehrere Treffer im selben Text
- Kein Treffer (Leerstring, ungültiger Input)
- Grenzfälle (z.B. Keyword in Bezeichner, Keyword in Kommentar, escaped Chars)

## Offene Punkte
- [ ] Review der Regex-Patterns auf Vollständigkeit
- [ ] Ggf. weitere Token (Zahlen, Typnamen) als Bonus ergänzen

### PR2 - CI und Workflow

**TITEL:** `feat: GitHub Actions CI-Pipeline eingerichtet`

**BESCHREIBUNG:**
## Was wurde geändert?
CI-Pipeline via GitHub Actions unter `.github/workflows/ci.yml` eingerichtet.

## Pipeline-Details
Drei Jobs, die sequenziell nach erfolgreichem Build laufen:
- **build** – kompiliert das Projekt (`classes`, `testClasses`)
- **test** – führt alle JUnit-Tests aus
- **format** – prüft die Codeformatierung via `spotlessCheck`

## Trigger
- Pull Requests
- Manuell via `workflow_dispatch`
- Kein automatischer Run bei Push in `main`
