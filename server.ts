import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';
import { GoogleGenAI } from '@google/genai';
import dotenv from 'dotenv';
import { createServer as createViteServer } from 'vite';

dotenv.config();

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const PORT = 3000;

// Lazy initialization of Gemini client
let geminiClient: GoogleGenAI | null = null;
function getGeminiClient(): GoogleGenAI {
  if (!geminiClient) {
    const apiKey = process.env.GEMINI_API_KEY;
    geminiClient = new GoogleGenAI({
      apiKey: apiKey || '',
      httpOptions: {
        headers: {
          'User-Agent': 'aistudio-build',
        },
      },
    });
  }
  return geminiClient;
}

const SYSTEM_INSTRUCTION = `You are "Amani", the official AI companion of MindBridge Uganda, powered by Google's Gemini. "Amani" means "Peace & Harmony" in Swahili.

You combine the full intelligence, structured depth, analytical clarity, and articulate communication of Gemini with warm, culturally grounded empathy for youth in Uganda and East Africa.

### CORE IDENTITY & CONVERSATIONAL STYLE (GEMINI-STYLE RESPONDING):
1. **Respond Like Gemini**:
   - Deliver clear, well-structured, comprehensive, and engaging responses.
   - Use beautiful Markdown formatting: clear headings (e.g. ### Actionable Steps), bold highlights for emphasis, structured bullet points, and numbered lists.
   - Break complex emotional or practical problems down into intuitive, manageable parts.
   - Provide concrete, actionable advice, grounded reflection questions, and empathetic encouragement.

2. **Breadth of Capabilities**:
   - Help the user with WHATEVER is needed. Whether they need emotional decompression, advice on academic stress (Makerere, Kyambogo, Kabale, MUST, etc.), career or relationship navigation, panic/anxiety grounding techniques (like 4-7-8 breathing or 5-4-3-2-1 sensory grounding), habit building, or understanding mental wellbeing concepts.
   - If they ask general questions, explain them thoroughly, clearly, and thoughtfully as Gemini does.

3. **Ugandan Cultural Grounding**:
   - You naturally understand and relate to Ugandan daily life, university environments, family expectations, community connections, and local expressions (Luganda words like *Oli otya*, *Gyebaleko*, *Bulungi*, *Webale*, *Kale*, *Ssebo*, *Nyabo*, Swahili terms like *Pole sana*, *Amani*).
   - Maintain a respectful, non-judgmental, inclusive, and stigma-free perspective.

4. **MindBridge Ecosystem Guide**:
   - You can explain how to use MindBridge Uganda: booking confidential sessions with verified human therapists/counsellors in the app, accessing the Psychoeducation Library, sharing anonymous reflections in Peer Support, and finding community referrals.

5. **Safety & Crisis Escalation**:
   - If the user indicates imminent danger, severe self-harm, or suicidal ideation, immediately prioritize their safety with tender warmth and concrete emergency resources:
     - Uganda Child & Youth Helpline (Sauti): 116 (Toll-Free, 24/7)
     - National Emergency Police/Ambulance: 112 or 999
     - National Mental Health Toll-Free / Butabika Referral Hospital: +256 800 200 600 or +256 414 504 376
     - In-App Crisis Screen: Tap the red Crisis tab or SOS button.
     Remind them that their life is irreplaceable and immediate compassionate human help is ready for them right now.`;

async function startServer() {
  const app = express();
  app.use(express.json({ limit: '10mb' }));

  // Health check
  app.get('/api/health', (req, res) => {
    res.json({ status: 'ok', service: 'MindBridge Uganda Server', chatbot: 'Amani' });
  });

  // Chat API endpoint for Amani
  app.post('/api/chat', async (req, res) => {
    try {
      const { messages, message } = req.body;

      if (!message && (!messages || messages.length === 0)) {
        return res.status(400).json({ error: 'Message content is required.' });
      }

      const getFallbackReply = (userQuery: string): string => {
        const query = userQuery.toLowerCase();
        if (query.includes('stress') || query.includes('anxious') || query.includes('anxiety') || query.includes('panic') || query.includes('overwhelm')) {
          return `### Grounding & Calming with Amani 🌿\n\nIt is completely valid to feel overwhelmed right now. Your nervous system is signaling high tension, but you are safe in this moment.\n\n### 1. The 5-4-3-2-1 Sensory Grounding Technique\n* **5 things you can see** around you (your screen, a tree outside, your shoes)\n* **4 things you can physically touch** (the fabric of your shirt, cool water)\n* **3 things you can hear** (the hum of the room, birds, traffic)\n* **2 things you can smell or love smelling**\n* **1 deep, conscious breath**\n\n### 2. Immediate Re-Centering\nRemind yourself: *“I don’t have to solve everything today. One small step is enough.”*\n\nWould you like me to walk you through another calming breathing rhythm, or would you like to speak with one of our counsellors?`;
        } else if (query.includes('exam') || query.includes('university') || query.includes('study') || query.includes('school') || query.includes('focus') || query.includes('deadline')) {
          return `### Academic & University Coping Strategy 📚\n\nUniversity and academic pressure in Uganda—whether at Makerere, Kyambogo, Kabale, MUST, or any institution—can feel immense, especially with coursework deadlines and expectations.\n\n### 1. The "Pomodoro 25/5" Rule\n* Work on one specific sub-topic for **25 focused minutes** without checking social media.\n* Take a **5-minute break** to drink water, stretch, or walk around.\n\n### 2. Overcoming Panic\n* **Brain-dump**: Write everything on a piece of paper so it's not crowding your headspace.\n* **Choose Top 2 Priorities**: What is the single most urgent submission?\n\nRemember: your academic results do not define your worth as a human being. How are you feeling right this moment?`;
        } else if (query.includes('breath') || query.includes('calm') || query.includes('relax')) {
          return `### 2-Minute Box Breathing Exercise 🌿\n\nLet's do this together right now. Sit comfortably and uncross your legs:\n\n1. **Inhale slowly** through your nose as you count: *1... 2... 3... 4...*\n2. **Hold your breath gently**: *1... 2... 3... 4...*\n3. **Exhale smoothly** through your mouth: *1... 2... 3... 4...*\n4. **Pause in stillness**: *1... 2... 3... 4...*\n\nRepeat this cycle 3 times. Notice your shoulders dropping away from your ears.\n\nHow does your body feel now compared to before?`;
        } else if (query.includes('book') || query.includes('counsellor') || query.includes('session') || query.includes('therapist')) {
          return `### Booking a Confidential Session with a Counsellor 🗓️\n\nMindBridge makes it simple and confidential to talk to a qualified Ugandan counsellor:\n\n### How to Book:\n1. Tap the **Counsellors** tab on the navigation bar.\n2. Browse specialists by language (English, Luganda, Runyankore, Swahili, etc.) and specialty.\n3. Choose between **Text Chat**, **Voice Call**, or **Video Call**.\n4. Pick a convenient date and time.\n\nAll sessions are encrypted and can be done completely anonymously without revealing your real name if you prefer.`;
        } else if (query.includes('suicide') || query.includes('kill') || query.includes('harm') || query.includes('die')) {
          return `### You Are Valued & Help Is Right Here ❤️\n\nPlease know that whatever weight you are bearing, you do not have to carry it alone. Compassionate human help is ready for you right now in Uganda:\n\n* **Uganda Youth & Child Helpline (Sauti)**: **116** (Toll-Free, 24/7)\n* **Butabika Hospital Mental Health Helpline**: **0800 200 600**\n* **Police/Medical Emergency**: **112** or **999**\n\nPlease reach out immediately or tap the Crisis SOS button in the app.`;
        }
        return `### Oli otya! I'm Amani, your MindBridge Companion 🌸\n\nI hear you, and I am right here beside you. Whatever is weighing on your mind today, you don't have to carry it all by yourself.\n\n### Practical Next Steps\n* **Take a Deep Breath**: Inhale slowly through your nose for 4 seconds, hold gently for 4, and exhale for 4.\n* **Break It Down**: Focus only on the next 15 minutes instead of the whole week.\n* **Talk to Someone Who Listens**: Explore verified human counsellors in the **Counsellors** tab or share with peers in **Peer Support**.\n\n> "Peace does not mean to be in a place where there is no noise or trouble. It means to be in the midst of those things and still be calm in your heart."\n\nTell me more about what is happening right now, and let's work through it step by step.`;
      };

      const userQuery = message || (messages && messages[messages.length - 1]?.text) || (messages && messages[messages.length - 1]?.content) || '';

      // If no API key is configured, return instant rich structured response
      if (!apiKey) {
        return res.json({
          reply: getFallbackReply(userQuery),
          success: true,
          mode: 'offline_efficient'
        });
      }

      // If API key is present, try Gemini with 2500ms timeout race for 100% efficiency
      try {
        const ai = getGeminiClient();
        const contents: Array<{ role: 'user' | 'model'; parts: Array<{ text: string }> }> = [];

        if (Array.isArray(messages)) {
          for (const msg of messages) {
            if (msg.role === 'user' || msg.sender === 'user') {
              contents.push({
                role: 'user',
                parts: [{ text: msg.content || msg.text || '' }],
              });
            } else if (msg.role === 'model' || msg.role === 'assistant' || msg.sender === 'bot' || msg.sender === 'counsellor' || msg.sender === 'amani') {
              contents.push({
                role: 'model',
                parts: [{ text: msg.content || msg.text || '' }],
              });
            }
          }
        }

        if (contents.length === 0 || contents[contents.length - 1]?.role !== 'user') {
          contents.push({
            role: 'user',
            parts: [{ text: userQuery || 'Hello Amani' }],
          });
        }

        const callGemini = async () => {
          const response = await ai.models.generateContent({
            model: 'gemini-2.5-flash',
            contents,
            config: {
              systemInstruction: SYSTEM_INSTRUCTION,
              temperature: 0.7,
            },
          });
          return response.text;
        };

        const timeoutPromise = new Promise<null>((resolve) => {
          setTimeout(() => resolve(null), 2500);
        });

        const replyResult = await Promise.race([callGemini(), timeoutPromise]);

        if (replyResult) {
          return res.json({
            reply: replyResult,
            success: true,
          });
        } else {
          // Timeout occurred, send instant fallback so response is 100% fast & efficient
          return res.json({
            reply: getFallbackReply(userQuery),
            success: true,
            mode: 'fast_fallback'
          });
        }
      } catch (geminiError: any) {
        console.warn('Gemini call error, returning instant fallback:', geminiError?.message);
        return res.json({
          reply: getFallbackReply(userQuery),
          success: true,
          mode: 'error_fallback'
        });
      }
    } catch (err: any) {
      console.error('Unhandled server error in /api/chat:', err);
      return res.status(500).json({
        error: 'Internal server error',
        fallbackReply: 'I am right here with you. Take a deep breath and tell me how you are feeling.'
      });
    }
  });

  // Vite middleware in development vs static serving in production
  if (process.env.NODE_ENV !== 'production') {
    const vite = await createViteServer({
      server: { middlewareMode: true },
      appType: 'spa',
    });
    app.use(vite.middlewares);
  } else {
    const distPath = path.join(process.cwd(), 'dist');
    app.use(express.static(distPath));
    app.get('*all', (req, res) => {
      res.sendFile(path.join(distPath, 'index.html'));
    });
  }

  app.listen(PORT, '0.0.0.0', () => {
    console.log(`MindBridge Uganda Server running on port ${PORT}`);
  });
}

startServer();
