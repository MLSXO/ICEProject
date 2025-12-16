# Snake Spil - Funktionelle Krav

## Projektbeskrivelse
Dette projekt er en implementation af det klassiske Snake spil i Java med fokus på forskellige frugttyper og power-up effekter.

## Funktionelle Krav

### 1. Grundlæggende Spillogik
Spilleren styrer en slange der bevæger sig kontinuerligt på spillebrættet og skal undgå at ramme sig selv eller væggene. Målet er at indsamle frugt for at score point og vokse i størrelse. Der er også en endless mode hvor slangen wrapper rundt om skærmen.

### 2. Slange Funktionalitet
Slangen vokser hver gang den spiser frugt og skifter farve afhængigt af frugttypen. Den bevæger sig automatisk og spilleren kan kun ændre retningen. Hastigheden påvirkes midlertidigt af visse frugter og power-ups.

### 3. Frugt System
Spillet har fem frugttyper med forskellige spawn-sandsynligheder og effekter:

**Æbler** (50% spawn) - 10 point, 1 segments vækst, grøn farve.

**Vindruer** (20% spawn) - 20 point, 2 segments vækst, lilla farve.

**Bananer** (15% spawn) - 3 segments vækst, gul farve, speed boost i 2 sekunder.

**Kirsebær** (10% spawn) - 1 segments vækst, pink farve. Giver 50 point hvis spist inden for 3 sekunder, ellers ingen point.

**Meloner** (5% spawn) - 2 segments vækst, orange farve, aktiverer tilfældig power-up.

### 4. Power-up Effekter
Tre power-ups aktiveres kun ved at spise meloner:

**Ghost power-up** - Immun mod selv-kollision i 3 sekunder.

**Slow power-up** - Sænker spillets hastighed i 5 sekunder for bedre kontrol.

**Double score power-up** - Fordobler alle point i 10 sekunder.

Kun én power-up kan være aktiv ad gangen med automatisk udløb.

### 5. Scoring og Game Over
Point tildeles baseret på frugttype (10-50 point) og ganges med 2 under double score power-up. Kirsebær har tidsbaseret bonus. Spillet slutter ved selv-kollision (medmindre ghost er aktiv) eller væg-kollision i normal mode. I endless mode wrapper slangen rundt om skærmen.

### 6. Visuel Præsentation og Kontrol
Spillet vises på et grid-baseret spillebræt hvor slangen skifter farve baseret på sidste spiste frugt. Der vises løbende score og aktive power-ups. Kirsebær har visuelle tidsindikator. Spilleren styrer med piltasterne og kan skifte mellem normal og endless mode.

## Tekniske Specifikationer
Udviklet i Java med Swing GUI og Timer-baseret game loop (120ms delay). Spillebrættet er et 40x30 grid med 20x20 pixel celler. Event-driven arkitektur med ActionListener implementation.

## Målgruppe
Egner sig til alle aldre der ønsker en moderne Snake-variant med strategi og timing-elementer gennem forskellige frugttyper og power-up kombinationer.
