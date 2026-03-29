## 1. Manifest and resources

- [x] 1.1 Add `android.permission.INTERNET` to `AndroidManifest.xml`
- [x] 1.2 Add string resources: GitHub profile URL (placeholder, e.g. `https://github.com/<your-username>` or `https://github.com`), button label, and fragment title as needed

## 2. Navigation and UI — home entry

- [x] 2.1 Add a button (or clearly labeled control) on `fragment_first.xml` for opening the GitHub profile view
- [x] 2.2 Register `GitHubProfileFragment` in `nav_graph.xml` with an action from `FirstFragment` to the new destination
- [x] 2.3 Wire the new button in `FirstFragment.kt` to navigate using the new action

## 3. GitHub profile screen

- [x] 3.1 Create `fragment_github_profile.xml` with a `WebView` as the main content
- [x] 3.2 Implement `GitHubProfileFragment.kt`: load URL from string resources, enable WebView settings (including JavaScript), set `WebViewClient`, handle lifecycle (e.g. clear WebView in `onDestroyView` if needed)
- [x] 3.3 Ensure Up/Back returns to the previous screen per `AppBarConfiguration` / navigation (toolbar/up as already used by `MainActivity`)

## 4. Verification

- [x] 4.1 Build and run: tap the home entry, confirm GitHub page loads in-app; press back, confirm return to home
- [x] 4.2 Replace placeholder URL in strings with a real profile URL and confirm it displays
