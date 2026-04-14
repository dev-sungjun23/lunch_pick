import { useState, useEffect } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { createTeam, createMember } from '../api.js';
import TeamForm from '../components/TeamForm.jsx';
import MemberForm from '../components/MemberForm.jsx';
import MembersList from '../components/MembersList.jsx';
import { loadMembers, saveMembers } from '../components/memberStorage.js';

export default function TeamSetupPage() {
  const { teamId: teamIdParam } = useParams();
  const navigate = useNavigate();
  const teamId = teamIdParam ? Number(teamIdParam) : null;

  const [teamName, setTeamName] = useState('');
  const [memberName, setMemberName] = useState('');
  const [members, setMembers] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (teamId && !Number.isNaN(teamId)) {
      setMembers(loadMembers(teamId));
    } else {
      setMembers([]);
    }
  }, [teamId]);

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
      const next = [...members, { id: res.id, name: res.name }];
      setMembers(next);
      saveMembers(teamId, next);
      setMemberName('');
    } catch (err) {
      setError(err.message || '팀원 추가 실패');
    } finally {
      setLoading(false);
    }
  }

  if (!teamId || Number.isNaN(teamId)) {
    return (
      <div style={{ padding: 16, maxWidth: 480 }}>
        <h1>팀 만들기</h1>
        <TeamForm
          teamName={teamName}
          onTeamNameChange={setTeamName}
          onSubmit={handleCreateTeam}
          loading={loading}
        />
        {error && <p style={{ color: 'crimson' }}>{error}</p>}
      </div>
    );
  }

  return (
    <div style={{ padding: 16, maxWidth: 480 }}>
      <h1>팀원 추가 (teamId: {teamId})</h1>
      <MemberForm
        memberName={memberName}
        onMemberNameChange={setMemberName}
        onSubmit={handleAddMember}
        loading={loading}
      />
      <MembersList members={members} />
      {error && <p style={{ color: 'crimson' }}>{error}</p>}
      <p>
        <Link to={`/team/${teamId}/lunch`}>점심 정하기 →</Link>
      </p>
    </div>
  );
}
