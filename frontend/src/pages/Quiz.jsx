// src/pages/Quiz.jsx
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import QuestionCard from "../components/QuestionCard.jsx";
import api from "../api/api";

function Quiz() {
  const location = useLocation();
  const navigate = useNavigate();
  const state = location.state;

  const [currentIndex, setCurrentIndex] = useState(0);
  const [answers, setAnswers] = useState({});
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!state || !state.quiz) {
      navigate("/", { replace: true });
    }
  }, [state, navigate]);

  if (!state || !state.quiz) {
    return null;
  }

  const { quiz, userEmail, userName } = state;
  const questions = quiz.questions || [];
  const currentQuestion = questions[currentIndex];

  const handleSelect = (option) => {
    setAnswers((prev) => ({
      ...prev,
      [currentQuestion.id]: option,
    }));
  };

  const goNext = () => {
    if (currentIndex < questions.length - 1) {
      setCurrentIndex((i) => i + 1);
    }
  };

  const goPrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex((i) => i - 1);
    }
  };

  const handleSubmit = async () => {
    setError("");

    if (Object.keys(answers).length === 0) {
      setError("Please answer at least one question before submitting.");
      return;
    }

    try {
      setSubmitting(true);
      const res = await api.post(`/quizzes/${quiz.id}/submit`, {
        userEmail,
        userName,
        answers,
      });

      navigate("/results", {
        state: {
          result: res.data,
        },
      });
    } catch (err) {
      console.error(err);
      const message =
        err.response?.data?.message ||
        "Something went wrong while submitting the quiz.";
      setError(message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <section className="page">
      <div className="quiz-layout">
        <div className="quiz-main">
          <QuestionCard
            question={currentQuestion}
            index={currentIndex}
            total={questions.length}
            selectedOption={answers[currentQuestion.id]}
            onSelect={handleSelect}
          />

          {error && <p className="error-text mt-8">{error}</p>}

          <div className="quiz-actions">
            <button
              type="button"
              className="secondary-button"
              onClick={goPrev}
              disabled={currentIndex === 0}
            >
              Previous
            </button>

            <div className="quiz-actions-right">
              <span className="progress-indicator">
                {Object.keys(answers).length} / {questions.length} answered
              </span>
              <button
                type="button"
                className="primary-button"
                onClick={
                  currentIndex === questions.length - 1
                    ? handleSubmit
                    : goNext
                }
                disabled={submitting}
              >
                {currentIndex === questions.length - 1
                  ? submitting
                    ? "Submitting..."
                    : "Submit Quiz"
                  : "Next"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Quiz;
