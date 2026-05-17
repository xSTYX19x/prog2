# 01 - Anonyme Klasen und lambdas

### 1. Sub

Für die Subtraktionsfunktion im Taschenrechner wurde der Aufbau von Add.java kopiert und auf Sub.java angepasst (Klassen- & Methodenname, Rechenoperation)

### 2. Mul (anonyme Klasse)

Für die anonyme Klasse wurde das Interface aus Operation.java verwndet. Damit wurde eine Instanz der anonymen Klasse erstellt, und in dieser kann die Rechenoperation durchgeführt werden. am ende gibt sie das erbgebnis zurück. In den Taschenrechenr eingebunden habe ich die anonyme Klasse mit `operations.put("Mul", Mul);` 

### 3. Div (lambda)

Für die lambda-Funktion **Div** musste nur in den `operations.put()`-Befehl der Name ("Div") und die lambda-Funktion ((a, b) -> a / b) eingefügt werden.

### 4. Anonyme Klasse -> lambda 

Beim Umwandeln der anonymen Klasse in eine lambda-Funktion wurde der Körper der anonymen Klasse entfernt und durch `(ActionEvent e)` ersetzt. Der funktionale Kern wurde einfach beibehalten.

# 02 - LockSnake

Das Projekt wurde geforkt und Gradle wie gehabt eingerichtet. 

### 1. UML-Diagram

Ein UML-Diagram wurde mit dem 'UML-Generator'-Plugin in intellij generiert und für mehr Übersichtlichkeit manuell arrangiert. Im UML-Diagram sind die einzenen im Projekt verwendetetn Klassen grafisch aufgelistet. In den Klassen sind alle Methoden inkl. Public (+) oder Private (-) und dem jeweiligen Datentypen angegeben. Außerdem sind die beziehungen zwischenden eunzelnen klassen durch gestrichelte oder durchgezogene linien mit pfeilen oder rauten angegeben. 

### 2. Game Engine & Game State

In `GameState.java` werden zunächst fünf private, finale Felder deklariert: `level`, `snake`, `pins`, `status` und `pendingDirection`. Für jedes dieser Felder existiert eine gleichnamige Getter-Methode, die den jeweiligen Wert zurückgibt.

Die zentrale Spiellogik befindet sich in der Methode `tick()`, die bei jedem Spielschritt ausgeführt wird. Zu Beginn wird geprüft, ob das Spiel aktiv ist und eine gültige Richtung vorliegt – andernfalls wird der aktuelle Zustand unverändert zurückgegeben. Anschließend berechnet `tick()` die nächste Kopfposition der Schlange und führt drei Kollisionsprüfungen durch:

- **Spielfeldgrenze**: Die Schlange verlässt das Spielfeld → Status wechselt auf `LOST_OUT_OF_BOUNDS`.
- **Wand**: Die Schlange bewegt sich gegen eine Wand → Bewegung wird blockiert, das Spiel läuft weiter.
- **Selbstkollision**: Der Kopf trifft auf einen eigenen Körperabschnitt → Status wechselt auf `LOST_SELF_COLLISION`.

Liegt keine dieser Bedingungen vor, folgt die Pin-Logik. Alle Pins werden durchlaufen und geprüft, ob der Schlangenkopf auf einem Pin landet. Ist das der Fall, wird überprüft, ob der Pin bereits auf `HIGH` steht oder die Anfahrtsrichtung nicht mit der benötigten `activationDirection` übereinstimmt – in beiden Fällen wird die Bewegung blockiert. Stimmt die Richtung hingegen überein und der Pin steht noch auf `LOW`, wird er auf `HIGH` gesetzt.

Abschließend wird geprüft, ob alle Pins auf `HIGH` stehen. Ist das der Fall, wechselt der Status auf `WON`. Andernfalls läuft das Spiel weiter. In beiden Fällen wächst die Schlange um ein Segment und ein neuer `GameState` wird zurückgegeben.

In `GameEngine.java` wird der aktuelle Spielzustand in dem Feld `state` gespeichert. Beim Erstellen einer neuen `GameEngine` wird ein neuer `GameState` mit dem geladenen Level, der Startposition der Snake, den Pins aus dem Level, dem Status `RUNNING` und der Richtung `NONE` angelegt.

Die Methode `update(Direction d)` wird verwendet, um eine neue Richtung aus der Tastatureingabe zu übernehmen. Dafür wird ein neuer `GameState` mit der alten Spiellage, aber mit der neuen Richtung erzeugt. Die Methode `tick()` führt dann einen Spielschritt aus, indem `state.tick()` aufgerufen wird.

### 3. Observer-Pattern

Für das Observer-Pattern wurde die Verbindung zwischen `GameEngine` und `GamePanel` aufgebaut. Das `GamePanel` wird in der `GameEngine` gespeichert und nach jeder Änderung des Spielzustands benachrichtigt. Dafür gibt es in `GameEngine.java` die Methode `notifyObservers()`. Wenn ein `GamePanel` vorhanden ist, wird dort `panel.update(state)` aufgerufen. Dadurch bekommt die Oberfläche automatisch den neuen Spielzustand und kann sich neu zeichnen.

In `GamePanel.java` gibt es passend dazu die Methode `update(GameState newState)`. In dieser Methode wird der alte Zustand durch den neuen Zustand ersetzt und anschließend `repaint()` aufgerufen. Dadurch ist das `GamePanel` der Observer für den `GameState` in der `GameEngine`.

Die zweite Observer-Richtung läuft von der Tastatur zum Spielmodell. Im `GamePanel` werden die Tastaturbelegungen aus `InputConstants.BINDINGS` gelesen und mit `setupKeyBindings` registriert. Wenn eine konfigurierte Taste gedrückt wird, ruft die Swing-Action `gameEngine.update(direction)` auf. Dadurch wird die `GameEngine` über eine neue `Direction` informiert und kann den Spielzustand aktualisieren.

Damit ist sichtbar:

- `GamePanel` beobachtet den Spielzustand der `GameEngine`.
- `GameEngine` reagiert auf Richtungsänderungen, die im `GamePanel` durch Tastatur-Events entstehen.

### 4. JUnit-Tests für GameState

Für die Spiellogik wurden in `GameStateTest.java` mehrere Unit-Tests geschrieben. Die Tests prüfen immer einen klaren Einzelfall und benutzen dafür kleine selbst erzeugte Level, damit sie nicht von externen Level-Dateien abhängig sind.

Folgende Fälle werden getestet:

- Wenn keine Richtung gesetzt ist, bleibt der Zustand unverändert.
- Wenn das Spiel bereits gewonnen ist, passiert bei einem Tick nichts mehr.
- Die Snake bewegt sich korrekt auf ein leeres Feld.
- Eine Wand blockiert die Bewegung.
- Das Verlassen des Spielfelds setzt den Status auf `LOST_OUT_OF_BOUNDS`.
- Eine Selbstkollision setzt den Status auf `LOST_SELF_COLLISION`.
- Ein `LOW`-Pin wird aus der richtigen Richtung aktiviert.
- Ein `LOW`-Pin blockiert aus der falschen Richtung.
- Ein bereits gesetzter `HIGH`-Pin blockiert ebenfalls.
- Das Spiel läuft weiter, wenn nach einem gesetzten Pin noch weitere Pins offen sind.
- Das Spiel wird gewonnen, wenn der letzte offene Pin gesetzt wurde.

Damit gibt es mindestens 10 Tests für `GameState`. Die Testnamen sind sprechend gewählt, z.B. `tickStopsAtWallWithoutMovingSnake`, `tickLosesOnSelfCollision` oder `tickWinsWhenLastOpenPinIsSet`. Die Hilfsmethoden `stateAt`, `emptyLevel`, `levelWithWall`, `cellsWithWall`, `pos` und `assertPosition` sorgen dafür, dass die Tests übersichtlich bleiben und die Testdaten reproduzierbar aufgebaut werden.

### 5. Lambda-Ausdrücke und Methodenreferenzen

Im LockSnake-Projekt werden mehrere Lambda-Ausdrücke verwendet. In `GameState.java` wird zum Beispiel beim Aktivieren eines Pins mit `pins.stream().map(...)` gearbeitet. Dabei wird per Lambda geprüft, welcher Pin an der aktuellen Position liegt und ob dieser auf `HIGH` gesetzt werden muss. Außerdem wird mit `allMatch(...)` geprüft, ob alle Pins gesetzt sind. Für die Selbstkollision wird ebenfalls ein Lambda verwendet, um die Körpersegmente der Snake mit der nächsten Position zu vergleichen.

Ein weiterer Lambda-Ausdruck steht in `GamePanel.java`. Dort werden die einzelnen KeyCodes mit `keyCodes.forEach(...)` in die Swing-InputMap eingetragen. Dadurch muss keine extra Schleife geschrieben werden.

Methodenreferenzen sind ebenfalls enthalten. In `GamePanel.java` wird `InputConstants.BINDINGS.forEach(this::setupKeyBindings)` verwendet, um die Tastaturbelegungen direkt an die Methode `setupKeyBindings` weiterzugeben. In `GameStateTest.java` wird in einem Stream mit `Pin::state` auf den Zustand der Pins zugegriffen und mit `Pin.State.HIGH::equals` geprüft, ob alle Pins gesetzt sind.

Damit enthält die Lösung mindestens drei Lambda-Ausdrücke und mindestens zwei Methodenreferenzen.

### 6. Abgabe-Checkliste

Die wichtigsten Punkte aus der Abgabe-Checkliste sind damit erfüllt:

- Wandbehandlung ist vorhanden, weil Bewegungen gegen `CellType.WALL` blockiert werden.
- Pin-Blockaden sind vorhanden, weil falsche Richtungen und bereits gesetzte Pins die Bewegung stoppen.
- Pin-Aktivierung ist vorhanden, weil ein `LOW`-Pin bei richtiger Richtung auf `HIGH` gesetzt wird.
- Selbstkollision ist vorhanden und führt zu `LOST_SELF_COLLISION`.
- Die Gewinnbedingung ist vorhanden, weil nach dem Setzen eines Pins geprüft wird, ob alle Pins `HIGH` sind.
- Es gibt mindestens 10 JUnit-Tests für `GameState`.
- Das Observer-Pattern ist zwischen `GameEngine` und `GamePanel` sichtbar umgesetzt.
- Die geforderten Lambda-Ausdrücke und Methodenreferenzen sind im Code enthalten.

Der Bonus mit Texturen, Musik, mehreren Leveln oder Level-Auswahl wurde nicht umgesetzt.
