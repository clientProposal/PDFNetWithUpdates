function initApryseSearch() {
  var script = document.createElement('script');
  script.setAttribute('type', 'text/javascript');
  script.setAttribute('src', 'https://prod.global-search.apryse.com/apryse-search.umd.js');

  script.onload = function() {
    var ele = document.getElementById('search_autocomplete');
    ele.setAttribute('readonly', 'readonly');

    window.apryseSearch.loadSearchTool({
      targetSelector: '#search_autocomplete',
      searchConfig: {
        tabs: [
          {
            name: 'API',
            filters: [
              { attribute: 'language', title: 'Language', default: 'Java' },
              { attribute: 'product', title: 'Product', default: 'Server SDK' },
            ],
          },
          {
            name: 'Guides',
            filters: [{ attribute: 'product', title: 'Product' }],
          },

          {
            name: 'Forum posts',
            filters: [{ attribute: 'product', title: 'Product' }],
          },
        ],
        defaultProduct: 'core'
      }
    })
  }
  document.body.appendChild(script);
}

document.addEventListener('DOMContentLoaded', initApryseSearch);