import { useParams, Link } from 'react-router-dom';
import ResultCard from '../components/ResultCard.jsx';

export default function ResultPage() {
  const { teamId: teamIdParam } = useParams();
  const teamId = Number(teamIdParam);

  if (Number.isNaN(teamId)) {
    return <p>잘못된 teamId</p>;
  }

  return (
    <div style={{ padding: 16, maxWidth: 480 }}>
      <h1>결과</h1>
      <p>
        <Link to={`/team/${teamId}/lunch`}>← 투표로</Link>
        {' · '}
        <Link to={`/team/${teamId}/setup`}>팀원 설정</Link>
      </p>
      <ResultCard teamId={teamId} />
    </div>
  );
}
