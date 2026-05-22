document.addEventListener('DOMContentLoaded', () => {

    // ---- LÓGICA DE FORMATOS EN CREAR PARTIDO ----
    // (Solo actúa si los botones existen en la página actual)
    const formatBtns = document.querySelectorAll('.format-btn');
    const inputFormato = document.getElementById('partido-formato');

    if (formatBtns.length > 0) {
        formatBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                formatBtns.forEach(b => {
                    b.classList.remove('selected');
                    b.style.border = '1px solid #cbd5e1';
                    b.style.color = '#64748b';
                });
                btn.classList.add('selected');
                btn.style.border = '2px solid var(--color-accent)';
                btn.style.color = 'var(--color-accent)';
                if (inputFormato) {
                    inputFormato.value = btn.getAttribute('data-format');
                }
            });
        });
    }

});