# PROMPTS — AI task and handoff prompts

Keep prompts/specifications separate from code and data.

Suggested folders:

```text
PROMPTS/
├── active/       Current task prompts used for development.
├── completed/    Prompts whose requested feature is accepted.
├── handoff/      Codex/AI onboarding and handoff packages.
└── templates/    Reusable analysis/build/test prompt templates.
```

A prompt should state:
- target game version
- baseline artifact
- required features
- constraints
- references to inspect
- expected output
- test criteria

Do not store secrets or personal data in prompts.
