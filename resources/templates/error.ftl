${container("containerTemplate", "PAGE_CONTAINER_SIMPLE")}
${container("title", "Error")}
${container("html", "error")}

<div class="wrapper">
    <h2 class="code">${code}</h2>
    <h3 class="error">${error}</h3>

    <a class="button" onclick="window.history.back();">Go Back</a>
</div>

<footer id="footer">
    <p id="powered-by">Made with &hearts; by <i>Onegai Sensei</i>.</p>
</footer>