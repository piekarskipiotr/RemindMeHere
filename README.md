# Remind Me Here 📍

## Opis

Remind Me Here to innowacyjna aplikacja mobilna 📱 zaprojektowana, aby pomóc Ci pamiętać o ważnych zadaniach i przypomnieniach w oparciu o Twoją lokalizację 🌍. Niezależnie od tego, czy potrzebujesz przypomnienia, aby kupić prezent 🎁 przechodząc obok ulubionego sklepu, czy chcesz zostać przypomniany o ważnym zadaniu po przybyciu do biura 🏢, Remind Me Here służy jako Twój osobisty asystent 🤖, zapewniając, że nic Ci nie umknie.

## Funkcje

- **Przypomnienia oparte na lokalizacji:** Ustawiaj przypomnienia dla konkretnych lokalizacji 📍, aby otrzymywać powiadomienia 🔔, gdy tam dotrzesz.
- **Przyjazny interfejs mapy:** Wybieraj lokalizacje dla swoich przypomnień bezpośrednio na mapie 🗺️.
- **Przyjazny interfejs użytkownika:** Czysty i intuicyjny interfejs użytkownika ułatwia zarządzanie twoimi przypomnieniami ✨.

## Wymagania

Aby aplikacja funkcjonowała poprawnie, konieczne jest przyznanie odpowiednich uprawnień. Remind Me Here wymaga stałego dostępu do Twojej lokalizacji 🛰️, aby przypominać Ci o zadaniach w zależności od tego, gdzie się znajdujesz.

### Specyfikacje techniczne
- **Zbudowane z Kotlin:** Rozwinięte przy użyciu Kotlina, nowoczesnego języka programowania, który zapewnia zwięzły, czytelny i bezpieczny kod.
- **Kotlin Coroutines:** Wykorzystuje Kotlin Coroutines do efektywnego programowania asynchronicznego, umożliwiając płynne i nieblokujące wykonywanie połączeń sieciowych, przetwarzanie danych i wiele więcej.
- **Architektura MVVM:** Przyjmuje architekturę Model-View-ViewModel (MVVM), ułatwiając modularność, testowalność i utrzymanie bazy kodu.
- **Hilt do wstrzykiwania zależności:** Wykorzystuje Hilt do uproszczonego wstrzykiwania zależności, upraszczając zarządzanie zależnościami i zapewniając skalowalny sposób organizacji architektury aplikacji.
- **ViewModel:** Wykorzystuje komponent ViewModel do zarządzania danymi związanymi z UI w sposób świadomy cyklu życia, zapewniając solidne i responsywne doświadczenie użytkownika.

Aby dopełnić opis aplikacji "Remind Me Here", dodam szczegółowe informacje o uprawnieniach, których wymaga aplikacja:

### Dodatkowe Informacje o Uprawnieniach

Aplikacja Remind Me Here wymaga następujących uprawnień, aby zapewnić pełną funkcjonalność i optymalne użytkowanie:

- **Dostęp do przybliżonej lokalizacji (`ACCESS_COARSE_LOCATION`):** Pozwala aplikacji na uzyskanie przybliżonej lokalizacji urządzenia, wykorzystując sieć i inne metody lokalizacji. Jest to przydatne, gdy dokładna lokalizacja GPS nie jest dostępna.
- **Dostęp do dokładnej lokalizacji (`ACCESS_FINE_LOCATION`):** Umożliwia aplikacji dostęp do dokładnej lokalizacji urządzenia za pomocą modułu GPS, co jest niezbędne do precyzyjnego określania, kiedy użytkownik znajduje się w wyznaczonej lokalizacji przypomnienia.
- **Dostęp do lokalizacji w tle (`ACCESS_BACKGROUND_LOCATION`):** To uprawnienie jest kluczowe dla działania aplikacji Remind Me Here, ponieważ umożliwia jej monitorowanie lokalizacji użytkownika nawet wtedy, gdy aplikacja nie jest aktywnie używana. Dzięki temu użytkownicy mogą otrzymywać powiadomienia o przypomnieniach na podstawie lokalizacji, niezależnie od tego, czy aplikacja jest otwarta, czy działa w tle.
- **Wysyłanie powiadomień (`POST_NOTIFICATIONS`):** Uprawnienie to pozwala aplikacji na wyświetlanie powiadomień na urządzeniu, co jest istotne dla funkcji przypomnień. Dzięki temu użytkownicy są natychmiast informowani o przypomnieniach poprzez powiadomienia, co zwiększa skuteczność aplikacji w przypominaniu o ważnych zadaniach i wydarzeniach.

Zapewnienie tych uprawnień jest niezbędne dla pełnej funkcjonalności aplikacji Remind Me Here, umożliwiając jej skuteczne śledzenie lokalizacji użytkownika i dostarczanie odpowiednich przypomnień w odpowiednim czasie i miejscu.

## Jak ustawić uprawnienia

### Uprawnienia do lokalizacji "Cały czas"

1. Przejdź do **Ustawień** ⚙️ na swoim urządzeniu i wybierz **Aplikacje** 📲.
2. Znajdź aplikację Remind Me Here na liście i wybierz ją.
3. Wybierz **Uprawnienia aplikacji** > **Lokalizacja** 📍.
4. Wybierz opcję **Zezwalaj cały czas**, aby umożliwić aplikacji wysyłanie przypomnień w oparciu o Twoją lokalizację, nawet gdy nie jest używana.
