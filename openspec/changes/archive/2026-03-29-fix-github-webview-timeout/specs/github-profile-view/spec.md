## ADDED Requirements

### Requirement: Visible loading state for GitHub profile page

The system SHALL provide a visible indication while the WebView is loading the configured URL.

#### Scenario: Loading indication on open

- **WHEN** the user opens the GitHub profile view
- **THEN** a loading indication is shown until the load finishes successfully or fails

## MODIFIED Requirements

### Requirement: GitHub profile view loads configured URL

The system SHALL load the GitHub page using a URL defined in application resources (for example a string resource), so the user can replace it with their own GitHub profile URL.

#### Scenario: WebView shows configured profile page

- **WHEN** the user opens the GitHub profile view
- **THEN** the application loads the configured URL inside an in-app web view

#### Scenario: Successful load after retry

- **WHEN** the user invokes retry after a failed load
- **THEN** the application attempts to load the same configured URL again

#### Scenario: Load does not complete

- **WHEN** the page cannot be loaded successfully (including network error or timeout)
- **THEN** the user SHALL see a non-empty error indication and SHALL be able to trigger a reload of the same URL
