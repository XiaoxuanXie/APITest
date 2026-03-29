## ADDED Requirements

### Requirement: Home screen exposes GitHub profile entry

The system SHALL provide a dedicated control on the application's home screen (the navigation start destination) that the user can use to open the in-app GitHub profile view.

#### Scenario: User sees entry on home screen

- **WHEN** the user views the home screen
- **THEN** a control is visible whose purpose is to navigate to the GitHub profile view

### Requirement: GitHub profile view loads configured URL

The system SHALL load the GitHub page using a URL defined in application resources (for example a string resource), so the user can replace it with their own GitHub profile URL.

#### Scenario: WebView shows configured profile page

- **WHEN** the user opens the GitHub profile view
- **THEN** the application loads the configured URL inside an in-app web view

### Requirement: User can return from GitHub profile view

The system SHALL allow the user to return from the GitHub profile view to the previous screen using the standard back navigation pattern for the app.

#### Scenario: Back from profile view

- **WHEN** the user is on the GitHub profile view and invokes back (system back or up navigation as implemented)
- **THEN** the user returns to the home screen or previous destination in the navigation stack

### Requirement: Network access for page load

The system SHALL declare network access required to load remote GitHub pages over HTTPS.

#### Scenario: Manifest allows HTTPS fetch

- **WHEN** the application is built and installed
- **THEN** the manifest includes permission for Internet access sufficient to load the configured HTTPS GitHub URL
