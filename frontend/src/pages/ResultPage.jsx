import { useParams, Link } from 'react-router-dom';
import ResultCard from '../components/ResultCard.jsx';

export default function ResultPage() {
  const { teamId: teamIdParam } = useParams();
  const teamId = Number(teamIdParam);

  if (Number.isNaN(teamId)) {
    return <p>잘못된 teamId</p>;
  }

  return (
    <div className="page-shell" style={{ padding: 16, maxWidth: 480, margin: '0 auto' }}>
      <h1>오늘의 점심 결과</h1>
      <p className="info-text">
        이 팀의 투표 결과를 바탕으로 최종 식당을 보여줍니다.
      </p>
      <p className="link-row">
        <Link to={`/team/${teamId}/lunch`}>← 투표 화면으로</Link>
        {' · '}
        <Link to={`/team/${teamId}/setup`}>팀원 다시 설정</Link>
      </p>
      <ResultCard teamId={teamId} />
    </div>
  );
}
