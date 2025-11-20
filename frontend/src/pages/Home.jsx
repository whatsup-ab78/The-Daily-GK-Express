// src/pages/Home.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

function Home() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleStart = async () => {
    setError("");

    if (!email.trim()) {
      setError("Please enter your email to start the quiz.");
      return;
    }

    try {
      setLoading(true);
      const res = await api.get("/quizzes/today");
      const quiz = res.data;

      navigate("/quiz", {
        state: {
          userEmail: email.trim(),
          userName: name.trim() || "GK User",
          quiz,
        },
      });
    } catch (err) {
      console.error(err);
      const msg =
        err.response?.data?.message ||
        "Unable to load today's quiz. Please try again.";
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="page page-center">
      <div className="card home-card">
        <h2 className="page-title">Boost your GK in 5 minutes</h2>
        <p className="page-subtitle">
          Daily India-focused current affairs quiz. One attempt per day. No
          fluff.
        </p>

        <div className="form-group">
          <label className="label">Email (required)</label>
          <input
            type="email"
            className="input"
            placeholder="you@example.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label className="label">Name (optional)</label>
          <input
            type="text"
            className="input"
            placeholder="Your name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>

        {error && <p className="error-text">{error}</p>}

        <button
          className="primary-button full-width"
          onClick={handleStart}
          disabled={loading}
        >
          {loading ? "Loading today’s quiz..." : "Start Today’s Quiz"}
        </button>

        <p className="helper-text">
          Your email is used only to track your daily attempt. No passwords, no
          login.
        </p>
      </div>
    </section>
  );
}

export default Home;
