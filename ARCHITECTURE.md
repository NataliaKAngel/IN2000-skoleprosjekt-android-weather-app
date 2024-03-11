ARCHITECTURE.md:
■ Beskriver arkitekturen som er benyttet i appen.
■ Beskrivelse av hvordan viktige objektorienterte prinsipper som
lav kobling og høy kohesjon samt design patterns som MVVM
og UDF er ivaretatt i løsningen burde også være med.
■ Beskriv løsningen beregnet på lesere som skal jobbe med drift,
vedlikehold og videreutvikling av løsningen. Beskriv hvilke
teknologier og arkitektur som brukes i løsningen. Beskriv hvilket
API-nivå (Android versjon) dere har valgt, og hvorfor.

-

-

- Etter nøye vurdering har vi bestemt oss for å bruke API-nivå 24 (Android 7.0, Nougat) som mål-API for vår Android-app. Denne beslutningen ble tatt med tanke på å balansere funksjonaliteten til appen med tilgjengeligheten for et bredt spekter av brukere. API-nivå 24 ble valgt på grunn av flere faktorer. For det første tilbyr dette API-nivået et solid sett med funksjoner og muligheter som vi kan dra nytte av for å øke appens ytelse, sikkerhet og brukeropplevelse. For det andre har API-nivå 24 en betydelig markedsandel og støttes av en stor del av Android-enheter i brukerbasen vår. Dette vil sikre at appen vår når ut til så mange brukere som mulig, samtidig som den opprettholder en god brukeropplevelse og funksjonalitet på tvers av ulike enheter. Ved å velge API-nivå 24 som mål-API, legger vi også til rette for en smidigere oppdateringsprosess i fremtiden, da dette API-nivået fortsatt er relevant og støttet av Android-plattformen. Vi snakket også om at andre API-nivåer også kunne tilby mye av de samme funksjonalitetene, men siden vi alle hadde erfaring med dette API-nivået fra tidligere obliger landet vi til slutt på API-nivå 24 (Android 7.0, Nougat).
