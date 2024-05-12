# MODELING.md 
 
## Innhold:  
 
I dette dokumentet vil vi beskrive de viktigste funksjonelle kravene til applikasjonen vår ved bruk av diagrammer og figurer. Vi beskriver brukerhistorier i tillegg til å gi et visuelt overblikk over hvordan dataen vil kunne flyte. Med veiledning fra veilederne har vi valgt å fokusere på ett mer omfattende funksjonelt krav og illustrere ulike brukerhistorier ut ifra dette.  
 
## Funksjonelle krav:  
 
Funksjonelt krav: Appen skal gi en jevnlig oppdatert og samlet anbefaling av kiteforholdene på ulike kiteplasser.  
Dette kravet innebærer at appen skal kunne gi en tydelig samlet anbefaling ved bruk av farge og figurer slik at brukere lett kan få en samlet vurdering. Ved at applikasjonen gjør denne utregningen, slipper brukerne å se på alle vær-data som er relevante for kiting, og deretter måtte bruke tid på å lage sin egen samlede vurdering. Dette gir spesielt en fordel til brukere som ikke har like stor erfaring innenfor kiting og dermed ikke vet hvilken informasjon og vær-data de skal se etter for å vurdere om det er gode kite-forhold. Dersom brukerne derimot har stor erfaring innenfor kiting og ønsker et mer detaljert vær-varsel i tillegg til vår vurdering, vil de relevante vær-dataene være tilgjengelig i applikasjon både som time for time og som langtidsvarsel. Formålet med kravet er dermed at det skal være en lett og effektivt å finne ut hvor det er mulig å kite.  
 
 
# Diagrammer:  
 
## Use-case:  
Slik det funksjonelle kravet også forklarer vil en kiter alltid starte med hjemskjermen vår som er et kart med predefinerte kiteplasser. I dette use-caset har vi tatt utgangspunkt i to typiske brukere; en som ansees som erfaren og en som ansees som ikke like erfaren. Begge brukere vil ha nytte av applikasjonen ved at den erfarne brukeren vil kunne få nytte av både en rask anbefaling og ekstra kite-relatert vær-data, mens den ikke så erfarne brukeren trykker direkte på kun de kiteplassene som er relevante (i den forstand at de har en god anbefaling den dagen). 

<img width="1150" alt="image" src="https://media.github.uio.no/user/9307/files/f79eed80-6b7f-49c5-9d81-ce132b8d57e5">


## Sekvensdiagram:  
I dette sekvensdiagrammet har vi fokusert på hvordan applikasjonen vår vil reagere på brukeren sine valg. Brukeren sine ønsker og valg vil bestemme hvilke klasser og filer som blir kalt og brukt. Dette gir en oversikt over hvordan brukere får tillgang på informasjonen de ønsker og hvor informasjonen kommer fra. Vi delte bilde i to for å kunne se hva det står på de ulike stedene.
<img width="1423" alt="image" src="https://media.github.uio.no/user/9307/files/a6775073-d3f4-4485-9cf7-b5af7fef6d2c">
<img width="1199" alt="image" src="https://media.github.uio.no/user/9307/files/a0626b35-2171-40f6-8e32-eda450a541eb">

## Klassediagram:  
Klassediagrammet vårt viser en overiskt over strukturen til klassene våre og hvordan de henger sammen. Det viser hvordan det er mulig å navigere seg fra de ulike klassene og hvor de får eventuell vær-data fra. Klassene farget grønt er klasser som inngår i brukerhistoriene inspirert av de funksjonelle kravene.  
<img width="813" alt="image" src="https://media.github.uio.no/user/9307/files/33a04dc5-c0cd-4c67-b1c2-37f714defe34">


## Aktivitetsdiagram:  
Aktivitetsdiagrammet vårt viser nok en gang hvordan mulige brukerhistorier kan utvikle seg og hvordan det henger sammen med de viktigste funksjonelle kravene. Dette diagrammet er et forsøk på illustrere de funksjonelle kravene på en mer “bevegelig” måte. Her kan vi se hvordan ulike beslutninger kan føre til at brukere for eksempel kan ende opp i sirkler. Ved å velge en kiteplass som gir dårig anbefaling og deretter velge å se etter enda en ny kiteplass, vil brukere kunne risikere å følge dette mønsteret til det ikke er flere kiteplaser igjen. Aktivitetdiagrammet viser også hvor det vil være ulike valgmuligheter og hva utfallene av disse valgene kan være.  

<img width="757" alt="image" src="https://media.github.uio.no/user/9307/files/62074d97-ddae-411e-8aeb-1b70473cb6cd">
