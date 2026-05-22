# Guía de Estilo y Sistema de Diseño (UI/UX) - 21GROUNDS

Actuando como un diseñador UI/UX experto, he definido este Sistema de Diseño pensado para ser moderno, dinámico y muy enfocado en la experiencia móvil, ideal para una aplicación deportiva como **21GROUNDS**. Está listo para ser trasladado a Figma y posteriormente a variables CSS.

---

## 1. Definición de Paleta de Colores

La paleta está diseñada para transmitir energía, competición y profesionalidad, contrastando colores vibrantes con fondos limpios.

### Colores de Marca
*   **Color Primario (Naranja Baloncesto):** 
    *   `HEX`: #FF6B00
    *   `RGB`: rgb(255, 107, 0)
    *   *Uso:* Botones principales, elementos de acción principal, detalles de acento.
*   **Color Secundario (Azul Marino Profundo):**
    *   `HEX`: #001F3F
    *   `RGB`: rgb(0, 31, 63)
    *   *Uso:* Botones secundarios, cabeceras, menús de navegación, elementos de alto contraste.
*   **Color Terciario (Azul Eléctrico - Apoyo):**
    *   `HEX`: #3B82F6
    *   `RGB`: rgb(59, 130, 246)
    *   *Uso:* Enlaces, iconos activos, estados seleccionados.

### Colores de Fondo y Superficie
*   **Fondo Principal (Gris Muy Claro):**
    *   `HEX`: #F8FAFC
    *   `RGB`: rgb(248, 250, 252)
    *   *Uso:* Fondo general de la aplicación.
*   **Fondo de Superficie (Blanco Puro):**
    *   `HEX`: #FFFFFF
    *   `RGB`: rgb(255, 255, 255)
    *   *Uso:* Tarjetas (cards), modales, formularios y menús desplegables.

### Colores de Texto
*   **Texto Principal (Casi Negro):**
    *   `HEX`: #0F172A
    *   `RGB`: rgb(15, 23, 42)
    *   *Uso:* Títulos y texto de alto énfasis.
*   **Texto Secundario (Gris Pizarra):**
    *   `HEX`: #64748B
    *   `RGB`: rgb(100, 116, 139)
    *   *Uso:* Párrafos, subtítulos, texto de apoyo y placeholders.

### Colores Semánticos (Feedback)
*   **Éxito (Verde Esmeralda):**
    *   `HEX`: #10B981 | `RGB`: rgb(16, 185, 129)
*   **Fondo Éxito (Verde Muy Claro):**
    *   `HEX`: #D1FAE5 | `RGB`: rgb(209, 250, 229)
*   **Error / Destructivo (Rojo Carmesí):**
    *   `HEX`: #EF4444 | `RGB`: rgb(239, 68, 68)
*   **Fondo Error (Rojo Muy Claro):**
    *   `HEX`: #FEE2E2 | `RGB`: rgb(254, 226, 226)

---

## 2. Definición de Tipografías y Jerarquías

Se utilizará **Inter** (disponible en Google Fonts), una tipografía sans-serif geométrica altamente legible en pantallas móviles y perfecta para interfaces modernas de alto rendimiento.

*   **Tipografía Principal:** `Inter`, sans-serif.

### Jerarquía Relativa (Base: 16px = 1rem)
Se define una escala modular clara para mantener la consistencia:

*   **H1 (Títulos de vista principal):**
    *   Tamaño: `1.5rem` (24px)
    *   Grosor: `800` (ExtraBold)
    *   Interlineado: `1.2`
*   **H2 (Subtítulos de sección / Títulos de tarjetas grandes):**
    *   Tamaño: `1.25rem` (20px)
    *   Grosor: `700` (Bold)
    *   Interlineado: `1.3`
*   **H3 (Títulos de tarjetas pequeñas / Etiquetas importantes):**
    *   Tamaño: `1.1rem` (17.6px)
    *   Grosor: `700` (Bold)
    *   Interlineado: `1.4`
*   **P (Párrafos / Texto base):**
    *   Tamaño: `0.95rem` (15.2px)
    *   Grosor: `400` (Regular)
    *   Interlineado: `1.5`
*   **Small (Textos de apoyo, etiquetas, metadatos, fechas):**
    *   Tamaño: `0.85rem` (13.6px)
    *   Grosor: `400` o `600` (Dependiendo del énfasis)
    *   Interlineado: `1.4`

---

## 3. Formularios, Controles e Iconografía

### Botones
Deben sentirse "clickeables" y accesibles (tamaño mínimo de toque recomendado en móviles: 44px).
*   **Botón Primario:**
    *   *Fondo:* Color Primario (#FF6B00)
    *   *Texto:* Blanco (#FFFFFF), centrado, uppercase (opcional para destacar), grosor `700`.
    *   *Bordes:* Radio de `8px` o `20px` (estilo píldora) sin borde extra.
    *   *Padding:* `0.8rem 1.5rem`.
*   **Botón Secundario:**
    *   *Fondo:* Color Secundario (#001F3F) o Fondo Superficie (#FFFFFF) con borde.
    *   *Texto:* Blanco (si fondo es #001F3F) o #001F3F (si es fondo blanco), grosor `700`.
    *   *Bordes:* Radio igual al primario.

### Campos de Entrada (Inputs y Selects)
El objetivo es ofrecer limpieza y evitar distracciones visuales hasta que el usuario interactúe.
*   *Fondo:* Blanco (#FFFFFF).
*   *Bordes:* 1px sólido, color Gris Claro (#CBD5E1).
*   *Radio de borde:* `8px`.
*   *Padding:* `0.8rem 1rem` (espacioso).
*   *Texto:* Tamaño `0.95rem`, color Texto Principal (#0F172A).
*   *Placeholder:* Color Texto Secundario (#64748B).
*   *Iconos internos:* A la izquierda (absolutos), centrados verticalmente, en color #94A3B8.

### Iconografía
*   **Estilo:** Iconos lineales (stroke/line icons), no rellenos (fill), con un grosor de trazo de `2px` y bordes redondeados (`stroke-linecap="round"`).
*   **Librería recomendada:** *Lucide Icons* o *Feather Icons*.
*   **Tamaño base:** `24x24px` para menús de navegación, `16x16px` o `18x18px` para iconos acompañando texto.

---

## 4. Definición de Estados Interactivos

Los cambios visuales deben ser rápidos y usar transiciones suaves (`transition: all 0.2s ease`).

### Estados de Botones
*   **Hover (Ratón encima):**
    *   *Primario (#FF6B00):* Oscurecer ligeramente a `#E65A00` o aplicar un brillo (brightness: 0.9). Aplicar un ligero desplazamiento hacia arriba (`transform: translateY(-1px)`) con sombra (`box-shadow: 0 4px 6px rgba(255, 107, 0, 0.25)`).
    *   *Secundario (#001F3F):* Oscurecer a `#001122` o aumentar la sombra.
*   **Active (Al hacer click):**
    *   Escalar ligeramente hacia abajo (`transform: scale(0.98)`).
*   **Disabled (Deshabilitado):**
    *   Fondo gris (`#E2E8F0`), texto gris (`#94A3B8`), sin eventos de puntero (`pointer-events: none`).

### Estados de Formularios (Inputs)
*   **Hover:**
    *   Borde se oscurece ligeramente a `#94A3B8`.
*   **Focus (Activo / Escribiendo):**
    *   Borde cambia al Color Terciario (#3B82F6) o Primario (#FF6B00).
    *   Sombra exterior suave (ring): `box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2)`.
    *   Fondo se mantiene blanco.

### Estados Semánticos (Validación de Formularios o Alertas)
*   **Error (Validación Incorrecta):**
    *   *Input:* Borde cambia a `#EF4444`. Sombra de foco cambia a `rgba(239, 68, 68, 0.2)`.
    *   *Mensaje:* Texto de ayuda en tamaño `0.75rem`, color `#EF4444`, aparece debajo del input.
    *   *Alertas/Banners:* Fondo `#FEE2E2`, Texto y bordes `#EF4444`.
*   **Éxito (Envío Correcto / Validación Positiva):**
    *   *Input:* Borde cambia a `#10B981`. (Normalmente se usa un pequeño icono de "check" verde a la derecha).
    *   *Alertas/Banners:* Fondo `#D1FAE5`, Texto `#10B981`. Suelen ser modales flotantes o *toast notifications* que desaparecen a los 3 segundos.
