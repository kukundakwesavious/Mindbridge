# MindBridge real-world photography

The app uses real-world photography from Unsplash for the hero, community, phone-user and counselling-session surfaces.

Sources:
- Therapist talking to a young woman on couch — Vitaly Gariev, Unsplash: https://unsplash.com/photos/therapist-talking-to-a-young-woman-on-couch-PYbWNtXcpjM
- A woman with an afro looking down at her cell phone — rayul, Unsplash: https://unsplash.com/photos/a-woman-with-an-afro-is-looking-down-at-her-cell-phone-SPcn3U14mjk
- Group of people on green grass field — Random Institute, Kampala, Uganda, Unsplash: https://unsplash.com/photos/group-of-people-on-green-grass-field-10D_1gUt65U
- A young girl talks to a therapist on a couch — Vitaly Gariev, Unsplash: https://unsplash.com/photos/a-young-girl-talks-to-a-therapist-on-a-couch-ObIW_W6jnXg

The URLs are kept in `src/data/media.js` so the prototype can remain lightweight. Each image also has a local fallback asset for offline/low-connectivity conditions. Before production, approved photos should be downloaded, optimized, rights-reviewed, and bundled locally.
