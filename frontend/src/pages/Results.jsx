// src/pages/Results.jsx
import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

function Results() {
  const location = useLocation();
  const navigate = useNavigate();
  const state = location.state;

  if (!state || !state.result) {
    return (
      <section className="page page-center">
        <div className="card">
          <p>No result data found. Please take the quiz first.</p>
          <button
            className="primary-button mt-16"
            onClick={() => navigate("/")}
          >
            Go to Home
          </button>
        </div>
      </section>
    );
  }

  const { totalQuestions, correct, incorrect, score } = state.result;
  const percent =
    totalQuestions > 0 ? Math.round((correct / totalQuestions) * 100) : 0;

  return (
    <section className="page page-center">
      <div className="card results-card">
        <h2 className="page-title">Your Daily GK Score</h2>
        <p className="page-subtitle">Nice! You completed today’s quiz.</p>

        <div className="results-grid">
          <div className="results-main-number">
            <span className="results-score">{score}</span>
            <span className="results-label">Score</span>
          </div>

          <div className="results-stats">
            <div className="results-row">
              <span>Total questions</span>
              <span>{totalQuestions}</span>
            </div>
            <div className="results-row">
              <span>Correct</span>
              <span className="text-green">{correct}</span>
            </div>
            <div className="results-row">
              <span>Incorrect</span>
              <span className="text-red">{incorrect}</span>
            </div>
            <div className="results-row">
              <span>Accuracy</span>
              <span>{percent}%</span>
            </div>
          </div>
        </div>

        <button
          className="secondary-button full-width mt-16"
          onClick={() => navigate("/")}
        >
          Back to Home
        </button>
      </div>
    </section>
  );
}

export default Results;
