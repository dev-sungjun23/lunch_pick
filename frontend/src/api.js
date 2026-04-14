const BASE_URL = 'http://localhost:8080';

async function request(path, options = {}) {
  const headers = { 'Content-Type': 'application/json', ...options.headers };
  const res = await fetch(`${BASE_URL}${path}`, { ...options, headers });
  const text = await res.text();
  if (!res.ok) {
    throw new Error(text || `${res.status} ${res.statusText}`);
  }
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return text;
  }
}

/** @param {{ name: string }} body */
export function createTeam(body) {
  return request('/api/teams', { method: 'POST', body: JSON.stringify(body) });
}

/** @param {{ teamId: number, name: string }} body */
export function createMember(body) {
  return request('/api/members', { method: 'POST', body: JSON.stringify(body) });
}

/**
 * Backend PreferenceSaveRequest: memberId, likeFoods, dislikeFoods
 * @param {{ memberId: number, likeFoods?: string[], dislikeFoods?: string[] }} body
 */
export function savePreference(body) {
  return request('/api/preferences', { method: 'POST', body: JSON.stringify(body) });
}

/**
 * Backend RecommendationRequest: teamId, location, maxDistance, budget
 * @param {{ teamId: number, location: string, maxDistance: number, budget: number }} body
 */
export function recommend(body) {
  return request('/api/recommendations', { method: 'POST', body: JSON.stringify(body) });
}

/** @param {{ memberId: number, restaurantId: number }} body */
export function castVote(body) {
  return request('/api/votes', { method: 'POST', body: JSON.stringify(body) });
}

/** @param {string|number} teamId */
export function decide(teamId) {
  return request(`/api/votes/teams/${teamId}/decision`, { method: 'POST' });
}
