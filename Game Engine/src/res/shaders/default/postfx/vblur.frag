#version 150
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;

void main() {
	outColor = textureOffset(colors, texCoord, ivec2(0, -5)) * 0.0093;
	outColor += textureOffset(colors, texCoord, ivec2(0, -4)) * 0.028002;
	outColor += textureOffset(colors, texCoord, ivec2(0, -3)) * 0.065984;
	outColor += textureOffset(colors, texCoord, ivec2(0, -2)) * 0.121703;
	outColor += textureOffset(colors, texCoord, ivec2(0, -1)) * 0.175713;
	outColor += texture(colors, texCoord) * 0.198596;
	outColor += textureOffset(colors, texCoord, ivec2(0, 1)) * 0.175713;
	outColor += textureOffset(colors, texCoord, ivec2(0, 2)) * 0.121703;
	outColor += textureOffset(colors, texCoord, ivec2(0, 3)) * 0.065984;
	outColor += textureOffset(colors, texCoord, ivec2(0, 4)) * 0.028002;
	outColor += textureOffset(colors, texCoord, ivec2(0, 5)) * 0.0093;
}
