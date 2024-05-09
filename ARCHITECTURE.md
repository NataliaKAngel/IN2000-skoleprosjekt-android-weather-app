
I dette dokumentet vil vi gå gjennom hva slags arkitektur og løsninger vi har benyttet oss av I applikasjonen vår. Vi vil forklare hvorfor vi har valgt disse løsningene og hvordan vi har brukt dem.

Arkitektur: 
Arkitekturen som er benyttet i vår app er designet for å oppnå både fleksibilitet, stabilitet og god systematikk. Vi har implementert den velkjente Model-View-ViewModel (MVVM) designmønsteret, kombinert med Universal Data Flow (UDF), for å oppnå en robust og skalerbar løsning.

Her er noen eksempler på hvordan MVVM og UDF arkitekturene er implementert i vår app:
Model-View-ViewModel (MVVM):
Modell (Model): Vi har separate klasser og modeller (for eksempel dataklasser) som håndterer dataene i appen vår. Disse modellene inneholder logikken for å hente, oppdatere og lagre dataene. Modellen i appen vår vil være ansvarlig for å kommunisere med Meteorologisk institutts API for å hente værdata. Dette kan inkludere å sende forespørsler til API-et og behandle responsene for å hente ut relevant værinformasjon som temperatur, vindretning, vindhastighet osv. 

Visning (View): Vi har aktiviteter og visninger som er ansvarlige for å vise dataene til brukeren. Disse visningene er passive og reagerer på endringer i modellen ved hjelp av databindings eller observatører. Visningen i appen vår vil være grensesnittet der brukeren ser værdataene. Dette kan være hoved-skjermen vår (HomeScreen) som viser kitespots i form av tomler. 

Visningsmodell (ViewModel): Vi har visningsmodeller som fungerer som mellomleddet mellom modellen og visningen. Disse visningsmodellene inneholder forretningslogikk (hvordan dataene blir presentert og behandlet i grensesnittet) knyttet til visningen og håndterer brukerhandlinger. De oppdaterer modellen og sender oppdateringer til visningen. Visningsmodellen vår fungerer som mellomleddet mellom modellen og visningen. Den inneholder forretningslogikken knyttet til visningen av værdataene og håndterer brukerhandlinger.
Visningsmodellen kan inneholde metoder for å hente værdata fra modellen og oppdatere visningen med disse dataene.
Den kan også inneholde logikk for å formatere værdataene på en leservennlig måte, for eksempel å legge til riktige units bak dataene, slik at brukere kan vite om det blir målt i m/s. I vår applikasjon finner du visningsmodellene med navn som ender på «ViewModel». Alle visninger/skjermer som viser informasjon hentet fra API-ene vil ha en tilhørende viewModel-fil. I disse filene henter vi data fra det samlede repositoriet samtidig som vi gjør dataen dynamisk ved å opprette nødvendige UI-states. 

2.	Universal Data Flow (UDF):
Vi har et ensartet og forutsigbart dataflytmønster gjennom hele appen. Dette betyr at data flyter fra de valgte API-ene til hver sin DataSource. Etter å ha hentet dataene fra DataSource, samler vi dataene i en felles Repository. Dette Repositoriet inneholder logikken for å administrere og tilgjengeliggjøre dataene for resten av appen.
Vi bruker enkeltkilde for sannhet (Single Source of Truth) for å sikre at dataene er konsistente og oppdaterte i hele appen.
Selv om vi behandler dataene i flere trinn, sikrer vi at DataSource er den primære kilden for værdataene våre. Dette betyr at når vi trenger å oppdatere, vil vi alltid hente dem fra denne kilden, og vi unngår dermed inkonsistenser og duplisering av data.
Ved å følge denne tilnærmingen opprettholder vi prinsippet om enkeltkilde for sannhet samtidig som vi benytter oss av flere lag av abstraksjon for å organisere og behandle dataene våre på en strukturert og modulær måte.

Ved å kombinere MVVM og UDF arkitekturene, oppnår vi en fleksibel og stabil løsning der dataene og visningen er godt separert, og endringer i en del av systemet ikke har uforutsette konsekvenser i andre deler. Dette gjør det enkelt å vedlikeholde og videreutvikle appen vår over tid.


Videre drift og vedlikehold: 
Ved å følge disse prinsippene har vi oppnådd lav kobling mellom komponentene i appen, noe som gjør det enkelt å endre eller oppgradere deler av systemet uten å påvirke andre deler. Samtidig har vi sikret høy kohesjon innenfor hver enkelt komponent, slik at relaterte funksjoner og ansvar holdes sammen.
For eksempel har vi et sett med metoder som er ansvarlige for å lage en såkalt «bottomsheet» på kartet, og disse metodene vil være strukturert på en måte som gjør det enkelt å forstå og vedlikeholde dem.
Videre bruker homeScreen disse metodene for å legge til denne ekstra-skjermen på kartet. Dette opprettholder lav kobling ved at homescreen ikke trenger å kjenne til de spesifikke detaljene om hvordan bottomsheeten blir laget, men i stedet bare kaller relevante metoder i components.kt.
Så selv om det ikke er en egen fil dedikert til bottomsheet, har vi fortsatt høy kohesjon innenfor dette området av koden ved å organisere relatert funksjonalitet sammen, og lav kobling ved å tillate at homescreen bruker denne funksjonaliteten uten å kjenne til de indre detaljene.

MVVM-arkitekturen har tillatt oss å separere brukergrensesnittet (View) fra forretningslogikken (ViewModel) og data lagret i modellen (Model). Dette gjør det enkelt å teste og vedlikeholde koden vår. UDF gir oss en enhetlig måte å håndtere dataflyten gjennom appen på, noe som gjør det enkelt å spore og feilsøke eventuelle problemer.

Når vi diskuterer vedlikehold og videreutvikling av vår app, er det viktig å se på hvordan våre valg av teknologier og arkitektur påvirker disse aspektene. Vår løsning er designet med tanke på å være fleksibel, skalerbar og enkel å vedlikeholde over tid, og dette gjenspeiles i flere områder av vår utviklingspraksis.
For det første tillater vår arkitektur, som er basert på MVVM og UDF, en klar separasjon av bekymringer og enkel organisering av koden. Dette gjør det lettere for utviklerne å forstå koden, finne feil og gjøre endringer uten å påvirke andre deler av systemet. For eksempel, ved å holde forretningslogikken i visningsmodellene, kan vi endre brukergrensesnittet uten å måtte endre koden som styrer logikken bak.
Videre er vår løsning bygget med tanke på skalerbarhet. Vi bruker moderne teknologier som støtter asynkron datastrøm og flertrådede operasjoner, slik at appen vår kan håndtere økende datamengder og kompleksitet uten å miste ytelse. Dette gjør det enkelt å legge til nye funksjoner eller utvide eksisterende funksjonalitet etter behov. I vår applikasjon vil dette være spesielt relevant for settings- og favourites-skjermene våre. Her har vi ikke implementert data fra API-ene og dermed har skjermene ingen viewModel. 
Ved å ha én primær kilde for dataene våre unngår vi inkonsistenser og duplisering av data. Dette gjør det enklere å vedlikeholde og oppdatere dataene, samtidig som det reduserer risikoen for feil.
Når det gjelder å legge til nye funksjoner eller utføre endringer i appen, har vårt team fulgt
en systematisk og grundig prosess. Vi starter alltid med grundige kravspesifikasjoner og designfaser for å sikre at vi forstår kravene og målene til endringen eller den nye funksjonen. Deretter implementerer vi endringene gradvis, ved hjelp av modulær og gjenbrukbar kode, og tester grundig for å sikre at alt fungerer som forventet. Vår løsning støttes også av en testinfrastruktur, som inkluderer enhetstester som hjelper oss med å identifisere og løse problemer tidlig i utviklingsprosessen. Dette gjør at vi kan utføre feilsøking og gjøre endringer med minimal risiko for uønskede bivirkninger.

Videre er det også viktig å forstå teknologiene og arkitekturen som brukes og hvorfor de brukes. Vi benytter oss av følgende moderne teknologier;

Kotlin: Kotlin er det primære programmeringsspråket som brukes for Android-utvikling. Det er et moderne språk som tilbyr mange fordeler, inkludert bedre lesbarhet, færre sjanser for feil, og mer koncis syntaks sammenlignet med Java.
Ktor: Ktor er et asynkront nettverksbibliotek som brukes for nettverkskommunikasjon i appen. Det tillater enkel og effektiv implementering av klient-serversystemer ved hjelp av Kotlin, noe vi tar bruk av når vi deserialiserer API-ene. 
Jetpack Compose: Jetpack Compose er et modern UI-toolkit for Android-utvikling som brukes til å bygge brukergrensesnittet i appen. Dette betyr at vi kan beskrive hvordan brukergrensesnittet skal se ut basert på tilstanden til appen og compose tar seg av resten, noe som gjør koden mer lesbar og forståelig. I tillegg kan compose-funksjoner deles og gjenbrukes på tvers av appen vår, noe som fører til en mer modulær og vedlikeholdbar kodebase. Vi har også brukt compose til å opprette egendefinerte UI-komponenter ved hjelp av sammensatte funksjoner, noe som reduserer behovet for gjentatt koding av lignende UI-elementer. 
Coroutines: Kotlin Coroutines brukes for å håndtere asynkron programmering i appen. Vi bruker coroutines i begge dataSourcene våre, noe vi kan se når vi bruker suspend nøkkelordet før funksjonsnavnet. Dette indikerer at disse funksjonene er designet for å bli kalt fra en coroutine eller en annen suspenderingsfunksjon.
I begge tilfeller gjør disse funksjonene nettverksforespørsler, som ofte er asynkrone operasjoner. Ved å bruke coroutines kan du gjøre disse asynkrone kallene på en enklere og mer konkurranse-orientert måte, og unngå å blokkere hovedtråden i appen din mens du venter på svar fra API-et. Dette hjelper til med å opprettholde appens responsivitet og ytelse.


Når det gjelder API-nivå, har vi valgt å støtte en rekke Android-versjoner for å nå et bredt spekter av brukere. Vi har imidlertid satt vår minste støttede API-versjon til Android 8.0 (API-nivå 26) for å dra nytte av de nyeste funksjonene og ytelsesforbedringene tilgjengelig i nyere versjoner, samtidig som vi sikrer kompatibilitet med et betydelig antall enheter.
Denne kombinasjonen av arkitektur, prinsipper og teknologier gir oss en solid plattform for å utvikle, vedlikeholde og skalere vår app på en effektiv måte.
