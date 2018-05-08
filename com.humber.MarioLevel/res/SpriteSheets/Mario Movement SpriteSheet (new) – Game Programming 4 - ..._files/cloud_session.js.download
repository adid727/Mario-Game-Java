var CloudSession = {
  initSessionHeartbeat:function (url) {
    // Ping the cloud session heartbeat URL if the page has been open/idling for 5 minutes
    setTimeout(function () {
      new Ajax.Request(url, { requestHeaders:{ "Suppress-Session-Timestamp-Update":true } });
    }, 300000);
  },
  checkCookie:function (cookiesEnabled, redirectUrl, seenLightbox, seenLightboxUrl, syncAndRefreshUrl) {
    if (!cookiesEnabled) {
      if (CloudSession.isOnlyAcceptCookiesFromVisitedSite()) {
        document.getElementById('cloudSessionForm').target = '_self';
        document.getElementById('cloudSessionForm').action = redirectUrl;
        document.getElementById('cloudSessionForm').submit();
      }
      else {
        if (!seenLightbox) {
          var lb = new lightbox.Lightbox({
            contents:{ id:'cloudCookieCheckLightboxContents'},
            title:page.bundle.getString('cookiecheck.title'),
            dimensions:{ w:400, h:300},
            onClose:function () {
              new Ajax.Request(seenLightboxUrl, {});
            }
          });
          lb.open();
        }
      }
    }
    else {
      CloudSession.tokenExchange(syncAndRefreshUrl);
    }
  },
  tokenExchange:function (syncAndRefreshUrl) {
    document.getElementById('cloudSessionForm').submit();
    new Ajax.Request(syncAndRefreshUrl, {
      onSuccess:function (response) {
        if (response.responseJSON) {
          if (response.responseJSON.optedOut) {
            // Reload the page - the user got opted out so the global nav will change
            window.location.reload();
          } else if (response.responseJSON.changedId) {
            var profileLink = $('profileAccessLink');
            if (profileLink) {
              profileLink.writeAttribute('data-profile-changed-id', response.responseJSON.changedId);
            }
          }
        }
      }
    });
  },
  isOnlyAcceptCookiesFromVisitedSite:function () {
    var userAgent = navigator.userAgent;
    var isSafari = userAgent.indexOf('Safari') >= 0 && userAgent.indexOf('Chrom') == -1; // 'Chrom' will get both Chrome and Chromium
    var isFirefox = userAgent.indexOf('Firefox') >= 0;
    return isSafari || isFirefox;
  }
};
