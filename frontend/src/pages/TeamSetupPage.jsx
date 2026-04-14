import { useState } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { createTeam, createMember } from '../api.js';
import TeamForm from '../components/TeamForm.jsx';
import MemberForm from '../components/MemberForm.jsx';
import MembersList from '../components/MembersList.jsx';
import useTeamMembers from '../hooks/useTeamMembers.js';

export default function TeamSetupPage() {
  const { teamId: teamIdParam } = useParams();
  const navigate = useNavigate();
  const teamId = teamIdParam ? Number(teamIdParam) : null;

  const [teamName, setTeamName] = useState('');
  const [memberName, setMemberName] = useState('');
  const { members, addMember } = useTeamMembers(teamId);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  async function handleCreateTeam(e) {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await createTeam({ name: teamName.trim() });
      navigate(`/team/${res.id}/setup`, { replace: true });
    } catch (err) {
      setError(err.message || '팀 생성 실패');
    } finally {
      setLoading(false);
    }
  }

  async function handleAddMember(e) {
    e.preventDefault();
    if (!teamId || Number.isNaN(teamId)) return;
    setError('');
    setLoading(true);
    try {
      const res = await createMember({ teamId, name: memberName.trim() });
      addMember({ id: res.id, name: res.name });
      setMemberName('');
    } catch (err) {
      setError(err.message || '팀원 추가 실패');
    } finally {
      setLoading(false);
    }
  }

  if (!teamId || Number.isNaN(teamId)) {
    return (
      <div style={{ padding: 16, maxWidth: 480, margin: '0 auto' }}>
        <h1>점심 팀 만들기</h1>
        <p style={{ margin: '4px 0 16px', color: '#555', fontSize: 14 }}>
          1) 팀을 만들고 → 2) 팀원을 추가한 뒤 → 3) 점심 추천 · 투표를 진행합니다.
        </p>
        <TeamForm
          teamName={teamName}
          onTeamNameChange={setTeamName}
          onSubmit={handleCreateTeam}
          loading={loading}
        />
        {error && <p style={{ color: 'crimson', marginTop: 8 }}>{error}</p>}
      </div>
    );
  }

  return (
    <div style={{ padding: 16, maxWidth: 480, margin: '0 auto' }}>
      <h1>팀원 추가 (teamId: {teamId})</h1>
      <p style={{ margin: '4px 0 16px', color: '#555', fontSize: 14 }}>
        팀원을 모두 추가했다면 아래 버튼으로 추천 · 투표 화면으로 이동하세요.
      </p>
      <MemberForm
        memberName={memberName}
        onMemberNameChange={setMemberName}
        onSubmit={handleAddMember}
        loading={loading}
      />
      <MembersList members={members} />
      {error && <p style={{ color: 'crimson', marginTop: 8 }}>{error}</p>}
      <p style={{ marginTop: 16 }}>
        <Link to={`/team/${teamId}/lunch`}>점심 정하기 화면으로 →</Link>
      </p>
    </div>
  );
}
