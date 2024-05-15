# Prosjektnavn 
 Kitevarsel
## Beskrivelse: 
Appen vår er en varslingsapp for kitere som forteller brukeren når og hvor man burde, og ikke burde kite. Gjennom brukerundersøkelse har kitere formidlet at de har savnet en app der man får all informasjon om værforholdet knyttet til kiting samlet på ett sted, og det er nettopp dette behovet vi ønsker å dekke. Appen presenterer et kart, hvor allerede definerte områder er markert med tomler. Disse områdene er populære kite steder på Sørlandet, som vi fikk kjennskap til gjennom brukerundersøkelse. På hvert område er det en tommel som enten er rød, oransje,grønn, gul, grå eller blå, og som enten peker opp eller ned. Dette representerer kite forholdet til det gitte området. Dersom brukeren ønsker å se nærmere på værvarselet og kite forholdet på et av områdene, er det bare å trykke på selve tommelen, og en mer detaljert værbeskrivelse blir presentert for brukeren. 
 
## Installasjon: 
1.	Dersom du allerede har Android Studios installert på maskinen din, så åpner du dette programmet. 
1.1.	Åpne prosjektet ditt og naviger til plasseringen av prosjektmappen. Velg prosjektmappen og klikk på «Open»
1.2.	Android Studios vil laste inn prosjektet og konfigurere det for bygging.  
1.3.	Klargjør en emulator eller enhet
1.3.1.	En kan teste appen på en fysisk Android-enhet eller ved bruk av emulator. For å konfigurere en emulator, gå til «Tools -> Device Manager» og opprett en ny virtuell enhet, helst en resizable emulator. 
1.4.	Start så appen på en enhet eller emulator. Dette gjør du ved å trykke på «Run App», eller kun Run og velger deretter hvilken emulator du vil kjøre appen på.
2.	Dersom du ikke har Android Studios på maskinen din, må du installere programmet, og dette gjør du gjennom developer.android.com
2.1.	Her finner du en detaljert beskrivelse om hvordan programmet skal lastes ned med hensyn til hva slags maskin du har. 
2.1.1.	Så velger du emulator (Resizable)
 


## Kjøring av applikasjonen 
Faktorer man må ta hensyn til ved kjøring av applikasjonen:
- URL-en vi valgte å bruke til MetAlers API-et er den som viser pågående farevarsler. Det er for tiden ingen utstedte farevarsler for våre valgte kite-områder. Dersom vedkommende ønsker å se hvordan appen blir med et pågående farevarsel, kan vedkommende endre URL-en i client.get i metoden getMetAlert() i MetAlertsDataSource fra METALERT_CURRENT til METALERT_EXAMPLE.
- For best brukeropplevelse anbefales det å bruke en liten til medium stor skjerm i emulatoren. 


Etter man har trykket på Run, så kommer det opp et kart. Det tar litt tid før markørene blir synlige på kartet, så det er bare å lene seg tilbake og vente i spenning. For å få mer informasjon om et spesifikt område, så er det bare å trykke på området og deretter trykke på «gå til spot». Da blir informasjon og værdata presentert. 

Andre faktorer - Warnings:
- Det er ubrukte variabler i WeatherAPIRepository som er nødvendige å opprette for at splitting av String skal skje riktig.
- Model-mappen: Vi har også advarsler som følge av ubrukte klasser som vi har opprettet for å deserialisere API-ene. 
- FavouriteScreen: Vi har ikke rukket å implementere funksjonaliteten, og derfor er FavouriteScreenViewModel ubrukt 
- KiteRecUnitTest: To warnings knyttet til at to variabler alltid har samme verdi. Det er fordi verdiene til variablene er hardkodet slik at det ble enklere å teste. I tillegg er to variabler ikke i bruk, årsaken til dette er den samme som for WeatherAPiRepository, altså at variablene er nødvendige for at splitting av String skal skje riktig. Den siste Warning er knyttet til en type mismatch (String? vs. String), men siden dette er en testfil så vet vi at vi alltid kommer til å sende inn en String. 
 
## Brukte biblioteker: List opp alle biblioteker og rammeverk du har brukt, sammen med deres versjoner og formål. 
Fra build.gradle:
1.	AndroidX Core KTX: Utvider Android-plattformen med Kotlin-koroutiner for raskere og enklere utvikling. Versjon: 1.12.0
2.	Ktor Client CIO: Et nettverksbibliotek for Kotlin og Android. Versjon:1.6.4
3.	AndroidX Lifecycle KTX: Kotlin-utvidelser for å jobbe med Lifecycle-komponentene i Android, som å håndtere livssyklusen til app-komponenter som aktiviteter og fragmenter. Brukes for å lage livssyklusbevisste dataobjekter. Versjon: 1.8.2
4.	AndroidX Activity Compose: Bibliotek for å integrere aktiviteter med Jetpack Compose. Versjon: 1.8.2.
5.	AndroidX Compose BOM: Bill of Materials (BOM) for å administrere Compose-relaterte biblioteker. Versjon: Inkludert i BOM.
6.	AndroidX Compose UI: Grunnleggende brukergrensesnitt for Jetpack Compose. Versjon: Inkludert i BOM.
7.	AndroidX Compose Material3: Material Design-komponenter for Jetpack Compose. Versjon: Inkludert I BOM.
8.	Junit: Rammeverk for enhetstesting. Versjon: 4.13.2
9.	Espresso: Et rammeverk for UI-testing av Android-apper. Versjon: 3.5.1
10.	Kotlin Coroutines Core og Android: Kotlin-biblioteker for asynkron programmering. Versjon: 1.7.1
11.	Ktor Client Content Negotiation: Ktor-utvidelse for å håndtere innholdsnegosiering. Versjon: Spesifiser av $ktor_version 
12.	Ktor Serialization Kotlinx JSON: Ktor utvidelse for å integrere Kotlinx JSON-serialisering. Versjon: Spesifisert av $ktor_version
13.	Kotlinx Serialization JSON: Kotlin-bibliotek for JSON-serialisering. Versjon:1.6.0
14.	AndroidX Naviagation Compose: Jetpack Navigation integrasjon for Jetpack Compose. Versjon: 2.7.6
15.	Mapbox Maps Android SDK: Bibliotek for å integrere kart og navigasjon i Android-apper. Versjon: 11.3.1
16.	Gson: Et JSON-bibliotek for Java og Android. Versjon.2.10.1
17.	AndroidX ConstraintLayout Compose: ConstraintLayout integrasjon for Jetpack Compose. Versjon: 1.0.1
18.	AndroidX AppCompat: Støttebibliotek for kompatibilitet med eldre Android-plattformer. Versjon: 1.3.0. 
19.	Ktor: Et nettverksbibliotek for Kotlin og Android. Brukes for å gjøre nettverkskall til en backend-tjeneste. 
20.	Jackson: JSON-bibliotek for Java og Kotlin.
21.	Font: En del av Jetpack Compose for å håndtere skrifttyper.
22.	Image og PainterResource: Deler av Jetpack Compose for å håndter bildevisning.
23.	ExperimentalMaterial3API: Del av Jetpack Compose for Material Design 3.0-komponenter.
24.	NAV Controller: En del av Jetpack Navigation for å administrere appens navigasjon.
25.	LocalTime og SimpleDataFormat: Java-klasser for håndtering av tid og dato. 
26.	LiveData og MutableLiveData: Deler av Android Architecture Component for å lage livssyklusbevisste dataobjekter.
27.	Dp, Sp og Em: Enheter brukt i Jetpack Compose for dimensjonsbehandling.
28.	AlertDialog og BottomSheet: Komponenter for dialoger og bunnskuffer i Jetpack Compose.
29.	Text: Komponent og tekstvisning i Jetpack Compose
30.	Switch og SwitchDefault: Komponenter for å lage brytere i Jetpack Compose. 
31.	ViewModel og viewModelScope: Deler av Android Architecture Components som brukes til å opprette og håndtere ViewModels og deres tilknyttede scopes for korutiner. 
32.	Flow-relaterte klasser(MutableStateFlow, StateFlow, asStateFlow, update): Deler av Kotlin Coroutines Flow API som brukes for å håndtere strømmer av data, spesielt i forbindelse med asynkron programmering.
33.	Bitmap, Canvas, Drawable: Android-grafikkrelaterte klasser som brukes til å manipulere bilder og tegne grafikk på skjermen.
34.	Mapbox-relaterte klasser (Point, CameraOptions, MapView, Style): Klasser og komponenter fra Mapboc Maps SDK som brukes til å integrere kartvisning og interaksjon i appen.
35.	ConstraintLayout-relaterte klasser (ConstraintLayout, ConstraintSet): Klasser for å opprette og håndtere komplekse layouter med ConstraintLayout-biblioteket i Jetpack Compose. 

NB!
- Flere av avhengighetene i build.gradle.kts filen har warnings knyttet til dem. Årsaken til dette er fordi det har kommet nyere versjoner av samtlige avhengigheter underveis i prosjektet. Vi har valgt å ikke endre avhengighetene til nyere versjoner ettersom vi ikke ønsket nye potensielle problemer å oppstå såpass nærme fristen. 

## Dokumentasjon 
Dokumentasjon som `ARCHITECTURE.md` og `MODELING.md` finnes som filer sammen med prosjektet, innenfor rotmappene. 

