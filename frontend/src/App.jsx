import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import TeamSetupPage from './pages/TeamSetupPage.jsx';
import LunchVotePage from './pages/LunchVotePage.jsx';
import ResultPage from './pages/ResultPage.jsx';

export default function App() {
  return (
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<TeamSetupPage />} />
      <Route path="/team/:teamId/setup" element={<TeamSetupPage />} />
      <Route path="/team/:teamId/lunch" element={<LunchVotePage />} />
      <Route path="/team/:teamId/result" element={<ResultPage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
    </BrowserRouter>
  );
}
