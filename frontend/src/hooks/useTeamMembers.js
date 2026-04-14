import { useEffect, useState } from 'react';
import { loadMembers, saveMembers } from '../components/memberStorage.js';

export default function useTeamMembers(teamId) {
  const [members, setMembers] = useState([]);

  useEffect(() => {
    if (teamId && !Number.isNaN(teamId)) {
      setMembers(loadMembers(teamId));
      return;
    }
    setMembers([]);
  }, [teamId]);

  function addMember(member) {
    if (!teamId || Number.isNaN(teamId)) return;

    setMembers((prev) => {
      const next = [...prev, member];
      saveMembers(teamId, next);
      return next;
    });
  }

  return { members, addMember };
}
