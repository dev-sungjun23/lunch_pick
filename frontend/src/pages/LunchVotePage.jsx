import { useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { recommend, castVote } from '../api.js';
import RecommendForm from '../components/RecommendForm.jsx';
import CandidateList from '../components/CandidateList.jsx';
import VoteActions from '../components/VoteActions.jsx';
import useTeamMembers from '../hooks/useTeamMembers.js';

export default function LunchVotePage() {
  const { teamId: teamIdParam } = useParams();
  const teamId = Number(teamIdParam);

  const [location, setLocation] = useState('회사');
  const [maxDistance, setMaxDistance] = useState('10');
  const [budget, setBudget] = useState('10000');
  const [candidates, setCandidates] = useState([]);
  const [memberId, setMemberId] = useState('');
  const { members } = useTeamMembers(teamId);
  const [error, setError] = useState('');
  const [loadingRec, setLoadingRec] = useState(false);
  const [loadingVote, setLoadingVote] = useState(false);
  const [voteOk, setVoteOk] = useState('');

  async function handleRecommend(e) {
    e.preventDefault();
    setError('');
    setVoteOk('');
    setLoadingRec(true);
    try {
      const body = {
        teamId,
        location: location.trim(),
        maxDistance: Number(maxDistance),
        budget: Number(budget),
      };
      if (Number.isNaN(body.maxDistance) || Number.isNaN(body.budget)) {
        throw new Error('도보/예산은 숫자로 입력하세요');
      }
      const list = await recommend(body);
      setCandidates(Array.isArray(list) ? list : []);
    } catch (err) {
      setCandidates([]);
      setError(err.message || '추천 실패');
    } finally {
      setLoadingRec(false);
    }
  }

  async function handleVote(e, selectedRestaurantId) {
    e.preventDefault();
    const can =
      selectedRestaurantId != null &&
      memberId !== '' &&
      !Number.isNaN(Number(memberId));
    if (!can) return;
    setError('');
    setVoteOk('');
    setLoadingVote(true);
    try {
      await castVote({
        memberId: Number(memberId),
        restaurantId: selectedRestaurantId,
      });
      setVoteOk('투표 저장됨');
    } catch (err) {
      setError(err.message || '투표 실패');
    } finally {
      setLoadingVote(false);
    }
  }

  if (Number.isNaN(teamId)) {
    return <p>잘못된 teamId</p>;
  }

  return (
    <div className="page-shell" style={{ padding: 16, maxWidth: 560, margin: '0 auto' }}>
      <h1>추천 · 투표</h1>
      <p className="info-text" style={{ marginBottom: 12 }}>
        팀원을 선택하고 원하는 후보에 투표하면, 결과 화면에서 최종 식당이 정해집니다.
      </p>
      <p className="link-row">
        <Link to={`/team/${teamId}/setup`}>← 팀원 설정</Link>
        {' · '}
        <Link to={`/team/${teamId}/result`}>결과 보기 →</Link>
      </p>

      <RecommendForm
        location={location}
        onLocationChange={setLocation}
        maxDistance={maxDistance}
        onMaxDistanceChange={setMaxDistance}
        budget={budget}
        onBudgetChange={setBudget}
        onSubmit={handleRecommend}
        loading={loadingRec}
      />

      <CandidateList candidates={candidates}>
        {({ selectedRestaurantId }) => (
          <VoteActions
            members={members}
            memberId={memberId}
            onMemberIdChange={setMemberId}
            selectedRestaurantId={selectedRestaurantId}
            candidatesLength={candidates.length}
            onSubmit={(e) => handleVote(e, selectedRestaurantId)}
            loadingVote={loadingVote}
            voteOk={voteOk}
          />
        )}
      </CandidateList>

      {error && <p className="error-text">{error}</p>}
    </div>
  );
}
