const key = (teamId) => `lunchpick_members_${teamId}`;

export function loadMembers(teamId) {
  try {
    return JSON.parse(sessionStorage.getItem(key(teamId)) || '[]');
  } catch {
    return [];
  }
}

export function saveMembers(teamId, members) {
  sessionStorage.setItem(key(teamId), JSON.stringify(members));
}
