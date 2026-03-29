## 1. WebView behavior

- [x] 1.1 Extend `WebViewClient` (or replace default) to handle `onPageStarted`, `onPageFinished`, `onReceivedError`, and HTTP errors where applicable; hide loading state on finish or error
- [x] 1.2 Tune `WebSettings`: e.g. `domStorageEnabled`, `mixedContentMode`, sensible cache mode; set a modern mobile Chrome-like `userAgent` if needed for GitHub compatibility

## 2. UI — loading and error

- [x] 2.1 Update `fragment_github_profile.xml`: overlay loading indicator and an error container (message + retry button) above the WebView
- [x] 2.2 In `GitHubProfileFragment`, wire visibility: show loading on load start, hide on success/failure; show error + retry on failure; `retry` reloads `getString(R.string.github_profile_url)`

## 3. Strings and verification

- [x] 3.1 Add string resources for generic load error and retry label if not inlined
- [x] 3.2 Build and manual test: open GitHub view on a working network; simulate airplane mode or bad URL to confirm error + retry; restore network and confirm reload succeeds
