# Recall
A private, widget-first app that helps me remember what I already learned, by resurfacing small personal insights at the right time.

## Core Product value:
1. Each insight is short, atomic, and personal (written in your own words).
2. Insights are connected to context (source + situation).
3. The app’s main job is surfacing, not browsing.

## Clear & Neat User Requirements
### 1. Insight Management
As a user, I want to:
- Create a short insight (1–2 sentences max)
- Attach:
    - Source type (book / podcast / video / article / personal)
    - Source name (free text)
    - Optional trigger/context
- Edit or delete insights anytime

Constraints:
- Insight text must be concise (hard limit, e.g. 200 characters)
- No rich formatting — plain text only

### 2. Tagging & Organization
As a user, I want to:
- Assign 1–3 simple tags per insight
- Filter insights by tag inside the app

Constraints:
- Tags are user-defined (no predefined taxonomy)
- No nested categories

### 3. Daily Random Insight (Core Feature)
As a user, I want to:
- Receive 1 random insight per day via widget
- Optionally tap the widget to see:
    - Full insight
    - Source
    - When I added it

Rules for randomness:
- Prefer insights:
    - Not shown recently
    - Marked as “important”
- Avoid repeating the same insight within a short window

### 4. Widget Behavior
As a user, I want the widget to:
- Auto-refresh daily at 06:00 AM (can adjust by config)
- Show:
    - Insight text
    - small source hint as logo (book / podcast / youtube vid)
- Be readable in <5 seconds

### 5. Memory Reinforcement Signals (Minimal)
As a user, I can:
- Mark an insight as:
    - 👍 Resonates today, add comment why it resonate (what today event relate to it)

System behavior:
- Frequently resonating insights cool down after a while
- insights with example relate event will shows with comment in the other day

### 6. Non-Goals (Explicit)
The app will not:
- Auto-summarize books or podcasts
- Recommend content
- Share insights publicly
- Track streaks or productivity metrics