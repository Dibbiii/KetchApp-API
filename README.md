Componenti del gruppo:

Nicolae Balaban <nicolae.balaban@studio.unibo.it>

Antonino Cardillo <antonino.cardillo2@studio.unibo.it>

Salvatore Persico <salvatore.persico4@studio.unibo.it>

Naman Bagga <naman.bagga@studio.unibo.it>

Obiettivo del progetto:

L'obiettivo principale di questo progetto è digitalizzare il classico gioco da tavolo "Gioco dell'oca" in chiave
moderna.

Requisiti minimi:

Creazione di un menu funzionale che permetta di scegliere tra tre opzioni:

Nuova partita
Carica Partita
Impostazioni
Esci
Finestra per la scelta del numero di giocatori (da 2 a 4). Inserimento del nickname per ogni giocatore, con annessa la
selezione della pedina.

Il gioco si svolge su un percorso composto da un numero finito di caselle. Alcune caselle sono speciali e possono
assegnare un bonus o un malus al giocatore che vi capita sopra.

I giocatori avanzano lungo il percorso in base al valore (1-6) del dado lanciato.

Esistono tre tipi di caselle:

Casella normale: indica semplicemente il numero della casella.
Casella speciale: ha un effetto randomico sul giocatore che ci finisce sopra. L'effetto può essere un bonus o un malus,
scelto casualmente da una lista di quattro effetti per tipo.
Casella prigione: si trova a metà del percorso. Il giocatore che vi finisce sopra resta bloccato per due turni, ma può
tentare di uscire lanciando il dado due volte di seguito; se ottiene due numeri uguali, può uscire immediatamente dalla
prigione.
Se la somma tra la posizione attuale del giocatore e il valore del lancio del dado supera l'ultima casella, il giocatore
tornerà indietro di un numero di caselle pari alla differenza tra la somma della posizione attuale, il valore del lancio
del dado e la posizione dell'ultima casella.

Il gioco termina quando uno dei giocatori raggiunge per primo l'ultima casella.

Requisiti opzionali:

Scelta della difficoltà: più malus e meno bonus.

Turno casuale all'inizio della partita.

Salvataggio e caricamento: il gioco deve permettere di riprendere la partita dallo stesso punto in cui è stata
interrotta, evitando di dover ricominciare ogni volta da capo.

Effetti sonori e musica:

Suono per il lancio del dado.
Musica di sottofondo regolabile nelle impostazioni.
Suoni personalizzati per le caselle speciali.
Una piccola finestra o area della GUI che mostra le mosse precedenti es:

"Giocatore 1 ha tirato un 5 ed è finito sulla casella 12."
"Giocatore 2 è entrato in prigione!"
Statistiche di gioco:

Numero di partite vinte per ogni giocatore.
Tempo medio di una partita.
Suddivisione dei ruoli:

Salvatore Persico: Gestione della logica di gioco (Game Logic)

Creazione delle classi principali, ad esempio:
Gioco (gestisce il flusso della partita)
Giocatore (contiene nickname, posizione, stato del giocatore)
Casella (classe generica per il tabellone con sottoclassi CasellaNormale, CasellaSpeciale, CasellaPrigione)
Logica del dado (simula il lancio del dado)
Implementazione delle regole del gioco, come il movimento dei giocatori e l'assegnazione dei bonus/malus.
Implementazione grafica della finestra in cui avviene la scelta del numero di giocatori e personalizzazione.
Naman Bagga: Interfaccia grafica (JavaFX UI)

Creazione grafica delle schermate principali con JavaFX:
Menu iniziale (Nuova partita, Carica partita, Impostazioni, Esci)[Solo Bottoni]
Visualizzazione del tabellone e gestione delle animazioni
Implementazione delle transizioni tra le schermate.
Antonino Cardillo: Gestione eventi e interazione utente

Implementazione dei listener per pulsanti e input utente (es. lancio del dado).
Connessione tra la logica del gioco e l'interfaccia grafica.
Implementazione della finestra grafica del turno giocatore con lancio del dado.
Nicolae Balaban: Salvataggio, caricamento e impostazioni

Creazione del sistema di salvataggio e caricamento.
Implementazione della gestione delle impostazioni (difficoltà, turni casuali, ecc.).
Creazione schermata grafica delle impostazioni e della scelta dei salvataggi.