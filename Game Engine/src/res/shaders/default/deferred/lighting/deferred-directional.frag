#version 150 core

in vec2 texCoord;
in vec3 cameraPos;

out vec4 outColor;

uniform vec3 lightColor;
uniform vec3 direction;

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;
uniform sampler2D specular;

void main() {
	vec3 position = texture(positions, texCoord).xyz;
	vec3 unitNormal = texture(normals, texCoord).xyz;
	float diffuse = max(0, dot(unitNormal, -direction));

	vec3 toCamera = normalize(cameraPos - position);
	vec3 lightOut = normalize(reflect(direction, unitNormal));

	vec4 specularVal = texture(specular, texCoord);

	float specularFactor = pow(max(0, dot(toCamera, lightOut)), specularVal.y);
	specularFactor *= specularVal.x;

	outColor = vec4(texture(colors, texCoord).rgb * (diffuse * lightColor) + (specularFactor * lightColor), 0) * specularVal.z;
}
